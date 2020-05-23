package com.dosto;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.dosto.App;
import com.dosto.models.User;
import com.dosto.models.Coder;
import com.dosto.services.UserService;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class LoginController {
    @FXML private TextField username;
    @FXML private TextField password;
    private List<User> users = UserService.getUsers();
    @FXML private void clickLogIn() throws  IOException{
        User formUser = new User(username.getText(),Coder.encode(password.getText()));
        for (User user : users) {
            if(user.equals(formUser)){
                if(user.getRole().equals("admin")){
                    App.setRoot("main-menu");
                }
                else App.setRoot("user-menu");
                System.out.println("Login successful");
                break;
            }else{
                System.out.println("Login failed");
            }
        }
    }

}