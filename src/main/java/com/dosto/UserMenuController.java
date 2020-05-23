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
    @FXML private AnchorPane userAnchor;
    @FXML private Tab tab2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTab, newTab) -> System.out.println("Tab changed")
        );
    }
}
