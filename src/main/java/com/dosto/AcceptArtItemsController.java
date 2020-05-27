package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.ArtItemService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AcceptArtItemsController implements Initializable {
    @FXML
    private TilePane tilePane;
    @FXML
    private Pagination pagination;
    private final int pageSize = 6;
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(ArtItemService.getPendingArtItems());

    public void createImageView(int index) {
        tilePane.getChildren().clear();
        for (int i = index * pageSize; i < index * pageSize + pageSize; i++) {
            try {
                ArtItem artItem = artItemList.get(i);
                VBox pane = new VBox();
                HBox root = new HBox();
                root.setPadding(new Insets(10, 10, 10, 10));
                JFXButton accept = new JFXButton("Accept");
                final Pane spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                spacer.setMinSize(10,1);
                JFXButton reject = new JFXButton("Reject");
                reject.setStyle("-fx-background-color: #4bc565");
                accept.setStyle("-fx-background-color: #4bc565");
                root.getChildren().addAll(accept,spacer,reject);
                int finalI = i;
                accept.setOnAction(actionEvent -> {
                        ArtItemService.acceptArtItem(artItem);
                        artItemList.remove(artItem);
                        if ((artItemList.size() / 6) + 1 > 5) {
                            pagination.setMaxPageIndicatorCount(5);
                        } else {
                            pagination.setMaxPageIndicatorCount(artItemList.size() / 6 + 1);
                        }
                        pagination.setPageCount(artItemList.size() / 6 + 1);
                        this.createImageView(pagination.getCurrentPageIndex());
                });
                reject.setOnAction(actionEvent -> {
                    ArtItemService.deleteArtItem(artItem);
                    artItemList.remove(artItem);
                    System.out.println(finalI);
                    if ((artItemList.size() / 6) + 1 > 5) {
                        pagination.setMaxPageIndicatorCount(5);
                    } else {
                        pagination.setMaxPageIndicatorCount(artItemList.size() / 6 + 1);
                    }
                    pagination.setPageCount(artItemList.size() / 6 + 1);
                    this.createImageView(pagination.getCurrentPageIndex());

                });
                pane.getChildren().add(root);
                ImageView imageView = new ImageView();
                Image image = artItem.getImage();
                imageView.setImage(image);
                imageView.setFitHeight(180);
                imageView.setFitWidth(180);
                pane.getChildren().add(imageView);
                Tooltip img = new Tooltip("Name: " + artItem.getName() + "\n" +
                        "Artist: " + artItem.getArtist() + "\n" +
                        "Description:" + artItem.getDescription());

                Tooltip.install(imageView, img);
                VBox pageBox = new VBox();
                pageBox.setPadding(new Insets(20, 20, 20, 20));
                pageBox.setSpacing(10);
                pageBox.getChildren().add(pane);
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
