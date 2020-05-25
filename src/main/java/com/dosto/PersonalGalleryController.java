package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.ArtItemService;
import com.dosto.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonalGalleryController implements Initializable {
    @FXML private TilePane tilePane;
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(ArtItemService.getArtItems());
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
        this.createImageView();
    }
    public void createImageView(){
        tilePane.getChildren().clear();
        for (ArtItem artItem : artItemList) {
            ImageView imageView = new ImageView();
            Image image = artItem.getImage();
            imageView.setImage(image);
            imageView.setFitHeight(180);
            imageView.setFitWidth(180);
            imageView.setSmooth(true);
            imageView.setCache(true);
            VBox pageBox = new VBox();
            pageBox.setPadding(new Insets(20,20,20,20));
            pageBox.setSpacing(10);
            pageBox.getChildren().add(imageView);
            tilePane.getChildren().add(pageBox);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createImageView();
    }
}
