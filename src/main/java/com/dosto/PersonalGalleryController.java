package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonalGalleryController implements Initializable {
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(new ArrayList<>());
    @FXML private void onAddArtItem() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addArtItemDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddArtItemDialogController dialogController = fxmlLoader.getController();
        dialogController.setAppMainObservableList(artItemList);

        Scene scene = new Scene(parent, 600, 400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
