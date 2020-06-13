package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.ArtItemService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersGalleryController implements Initializable {

    @FXML
    private TilePane tilePane;
    @FXML
    private Pagination pagination;
    private final int pageSize = 6;
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(ArtItemService.getAcceptedArtItems());

    public void createImageView(int index) {
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

    public VBox createStuff(int index) {
        createImageView(index);
        return new VBox();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createImageView(0);
        System.out.println(artItemList.size() / 6);
        if ((artItemList.size() / 6) + 1 > 5) {
            pagination.setMaxPageIndicatorCount(5);
        } else {
            pagination.setMaxPageIndicatorCount(artItemList.size() / 6 + 1);
        }
        pagination.setPageCount(artItemList.size() / 6 + 1);
        pagination.setPageFactory(this::createStuff);
    }
}

