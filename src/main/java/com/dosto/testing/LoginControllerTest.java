package com.dosto.testing;

import com.dosto.App;
import com.dosto.LoginController;
import com.dosto.models.Coder;
import com.dosto.models.User;
import com.dosto.services.GlobalVars;
import com.dosto.services.UserService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LoginControllerTest {
    List<User> users = UserService.getUsers();
    private String findUser(User formUser){
        String root = "";
        for (User user : users) {
            if(user.equals(formUser)){
                if(user.getRole().equals("admin")){
                    root = "main-menu";
                }
                else root = "user-menu";
                break;
            }
        }
        return root;
    }
    @Test
    public void loginSuccess() throws IOException {
        User user = new User("admin",Coder.encode("admin"));

        String value = findUser(user);
        Assert.assertEquals(value,"main-menu");


        user = new User("test",Coder.encode("1234"));

        value = findUser(user);
        Assert.assertEquals(value,"user-menu");



    }
}