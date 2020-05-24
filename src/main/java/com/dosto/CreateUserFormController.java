package com.dosto;

import com.dosto.models.User;
import com.dosto.services.UserService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserFormController implements Initializable {

    @FXML private JFXTextField firstName;
    @FXML private JFXTextField lastName;
    @FXML private JFXTextField username;
    @FXML private JFXTextField email;
    @FXML private JFXPasswordField password;
    @FXML private JFXComboBox<String> comboRole;

    private ObservableList<User> appMainObservableList;
    void setAppMainObservableList(ObservableList<User> tvObservableList) {
        this.appMainObservableList = tvObservableList;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

   @FXML
   private void onSubmit (ActionEvent event){
        User userRequest = new User(username.getText(),password.getText(), comboRole.getValue(),email.getText(),lastName.getText(),firstName.getText());
        appMainObservableList.add(userRequest);
       UserService.addUser(userRequest);
        this.closeStage(event);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboRole.setItems(FXCollections.observableArrayList("admin","user"));
    }
}
