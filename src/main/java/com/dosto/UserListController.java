package com.dosto;

import com.dosto.models.User;
import com.dosto.services.UserService;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> username;
    @FXML private TableColumn<User, String> role;
    public UserListController(){
        System.out.println("User Constructed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        userTable.setItems(FXCollections.observableArrayList(UserService.getUsers()));

    }
}
