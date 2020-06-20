package com.dosto;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.dosto.App;
import com.dosto.models.User;
import com.dosto.models.Coder;
import com.dosto.services.GlobalVars;
import com.dosto.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label errorLabel;
    private List<User> users = UserService.getUsers();
    @FXML private void onKeyDown(KeyEvent event) throws IOException {
        if(event.getCode().equals(KeyCode.ENTER))
            clickLogIn();
    }

    @FXML private void clickLogIn() throws  IOException{
        User formUser = new User(username.getText(),Coder.encode(password.getText()));
        for (User user : users) {
            System.out.println(user.getPassword());
            System.out.println(formUser.getPassword());
            if(user.equals(formUser)){
                GlobalVars.loggedUser = user;
                if(user.getRole().equals("admin")){
                    App.setRoot("main-menu");
                }
                else App.setRoot("user-menu");
                break;
            }else{
                errorLabel.setText("Invalid password or username");
            }
        }
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }
}