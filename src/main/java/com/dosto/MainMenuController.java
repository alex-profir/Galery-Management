package com.dosto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.dosto.App;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class MainMenuController implements Initializable {
    @FXML private JFXTabPane tabPane;
    @FXML private AnchorPane userAnchor;
    @FXML private Tab userTab;
    @FXML private Tab tab2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observableValue, Tab oldTab, Tab newTab) {
                        System.out.println("Tab changed");
                        if (newTab.getContent() != null) {
                            try {
                                System.out.println(newTab.equals((userTab)));
                                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(("users" + ".fxml")));
                                newTab.setContent(fxmlLoader.load());
                            } catch (IOException e) {
                                System.out.println(e.toString());
                            }
                        }
                        tabPane.requestLayout();
                    }
                }
        );
    }
}