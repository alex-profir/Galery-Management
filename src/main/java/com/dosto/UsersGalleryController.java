package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.ArtItemService;
import com.dosto.services.GlobalVars;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersGalleryController implements Initializable {

    @FXML private TilePane tilePane;
    @FXML private Pagination pagination;
    @FXML private JFXButton borrowButton;
    private final int pageSize = 6;
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(ArtItemService.getAcceptedArtItems());
    @FXML private void handleBorrowClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("borrow.fxml"));
        Parent parent = fxmlLoader.load();
        BorrowArtItemController dialogController = fxmlLoader.getController();

        Scene scene = new Scene(parent, 600, 400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    private void createImageView(int index) {
        tilePane.getChildren().clear();
        for (int i = index * pageSize; i < index * pageSize + pageSize; i++) {
            try {
                ArtItem artItem = artItemList.get(i);
                ImageView imageView = new ImageView();
                Image image = artItem.getImage();
                imageView.setImage(image);
                imageView.setFitHeight(180);
                imageView.setFitWidth(180);
                Tooltip img = new Tooltip("Name: " + artItem.getName() + "\n" +
                        "Artist: " + artItem.getArtist() + "\n" +
                        "Description:" + artItem.getDescription() + "\n" +
                        "Owner:"+artItem.getOwner());

                Tooltip.install(imageView, img);
                VBox pageBox = new VBox();
                pageBox.setPadding(new Insets(20, 20, 20, 20));
                pageBox.setSpacing(10);
                pageBox.getChildren().add(imageView);
                tilePane.getChildren().add(pageBox);
            } catch (IndexOutOfBoundsException e) {
                e.getSuppressed();
            }

        }
    }

    private VBox createStuff(int index) {
        createImageView(index);
        return new VBox();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(GlobalVars.loggedUser.getRole().equals("admin")){
            borrowButton.setVisible(false);
        }
        this.createImageView(0);
        pagination.setMaxPageIndicatorCount(Math.min((artItemList.size() / 6) + 1, 5));
        pagination.setPageCount(artItemList.size() / 6 + 1);
        pagination.setPageFactory(this::createStuff);
    }
}

