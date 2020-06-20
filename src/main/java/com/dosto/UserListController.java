package com.dosto;

import com.dosto.models.User;
import com.dosto.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserListController implements Initializable {
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> username;
    @FXML private TableColumn<User, String> role;
    @FXML private TableColumn<User, String> lastName;
    @FXML private TableColumn<User, String> firstName;
    @FXML private TableColumn<User, String> email;

    private ObservableList<User> observableUsers = FXCollections.observableArrayList(UserService.getUsers());

    @FXML private void onCreateUser() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-user-form.fxml"));
        Parent parent=loader.load();
        CreateUserFormController userFormController = loader.getController();
        userFormController.setAppMainObservableList(observableUsers);
        Scene scene= new Scene(parent,600,350);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public UserListController(){
        System.out.println("User Constructed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable.setItems(observableUsers);

    }
}
