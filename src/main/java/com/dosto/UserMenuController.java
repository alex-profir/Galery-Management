package com.dosto;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    @FXML
    private JFXTabPane tabPane;
    @FXML private Tab personalGallery;
    @FXML private Tab usersGallery;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> {
                    if (newTab.getContent() != null) {
                        try {
                            if(newTab.equals(personalGallery)) {
                                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(("personal-gallery.fxml")));
                                newTab.setContent(fxmlLoader.load());
                            } else if(newTab.equals(usersGallery)){
                                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(("users-gallery.fxml")));
                                newTab.setContent(fxmlLoader.load());
                            }
                        } catch (IOException e) {
                            System.out.println(e.toString());
                        }
                    }
                    tabPane.requestLayout();
                }
        );
    }
}
