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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUserFormController implements Initializable {

    @FXML private JFXTextField firstName;
    @FXML private JFXTextField lastName;
    @FXML private JFXTextField username;
    @FXML private JFXTextField email;
    @FXML private JFXPasswordField password;
    @FXML private JFXComboBox<String> comboRole;
    @FXML private Label error;

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
        boolean isValidForm= true;
        if(username.getText().trim().length()==0){
            error.setText("Please enter a valid username");
            isValidForm=false;
        }
       if(firstName.getText().trim().length()==0){
           error.setText("Please enter a valid first name");
           isValidForm=false;
       }
       if(lastName.getText().trim().length()==0){
           error.setText("Please enter a valid last name");
           isValidForm=false;
       }
       if(password.getText().trim().length()==0){
           error.setText("Please enter a valid password");
           isValidForm=false;
       }
       if(email.getText().trim().length()==0){
           error.setText("Email length must be greater than 0");
           isValidForm=false;
       }
       else {
           String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
           Pattern pattern = Pattern.compile(regex);
           Matcher matcher = pattern.matcher(email.getText());
           if (!matcher.matches()) {
               error.setText("Invalid email");
               isValidForm=false;
           }
       }
        User userRequest = new User(username.getText(),password.getText(), comboRole.getValue(),email.getText(),lastName.getText(),firstName.getText());
        for(User user:appMainObservableList){
                if(user.getUsername().equals(userRequest.getUsername())){
                    error.setText("Username unavailable");
                    isValidForm=false;
                }
            }
       if(isValidForm) {
           appMainObservableList.add(userRequest);
           UserService.addUser(userRequest);
           this.closeStage(event);
       }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboRole.setItems(FXCollections.observableArrayList("admin","user"));
        comboRole.setValue("user");
    }
}
