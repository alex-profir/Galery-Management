package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.models.Borrow;
import com.dosto.services.ArtItemService;
import com.dosto.services.BorrowService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalGalleryController implements Initializable {
    @FXML
    private TilePane tilePane;
    @FXML
    private Pagination pagination;
    private final int pageSize = 6;
    private ObservableList<ArtItem> artItemList = FXCollections.observableArrayList(ArtItemService.getLoggedUserArtItems());

    @FXML
    private void onAddArtItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addArtItemDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddArtItemDialogController dialogController = fxmlLoader.getController();
        dialogController.setAppMainObservableList(artItemList);

        Scene scene = new Scene(parent, 600, 400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        pagination.setMaxPageIndicatorCount(Math.min((artItemList.size() / 6) + 1, 5));
        pagination.setPageCount(artItemList.size() / 6 + 1);
        this.createImageView(pagination.getCurrentPageIndex());
    }
    private void onReview(ActionEvent event,Borrow borrow) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("borrow.fxml"));
        Parent parent = fxmlLoader.load();
        BorrowArtItemController dialogController = fxmlLoader.getController();

        dialogController.setBorrow(borrow);

        Scene scene = new Scene(parent, 600, 400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        artItemList = FXCollections.observableArrayList(ArtItemService.getLoggedUserArtItems());
        this.createImageView(0);
    }
    private void onReturn(Borrow borrow) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("borrow.fxml"));
        Parent parent = fxmlLoader.load();
        BorrowArtItemController dialogController = fxmlLoader.getController();

        dialogController.setReturnBorrow(borrow);

        Scene scene = new Scene(parent, 600, 400);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        // refresh page
        artItemList = FXCollections.observableArrayList(ArtItemService.getLoggedUserArtItems());
        this.createImageView(0);
    }
    private void createImageView(int index) {
        tilePane.getChildren().clear();
        for (int i = index * pageSize; i < index * pageSize + pageSize; i++) {
            try {
                ArtItem artItem = artItemList.get(i);
                Borrow borrow = BorrowService.getBorrowByArtItem(artItem);

                VBox pane = new VBox();
                HBox root = new HBox();
                root.setPadding(new Insets(10, 10, 10, 10));
                JFXButton review = new JFXButton();
                JFXButton returnButton = new JFXButton();
                review.setStyle("-fx-background-color: #121212");
                Label status = new Label();
                status.setStyle("-fx-background-color: #4bc565; -fx-text-inner-color: #a9a9a9;");
                switch (artItem.getStatus()) {
                    case ArtItem.pendingStatus:
                        status.setText("PENDING");
                        break;
                    case ArtItem.borrowedStatus:
                        status.setText("BORROWED");

                        returnButton.setText("Return Items");
                        returnButton.setStyle("-fx-background-color: #4bc565");
                        returnButton.setOnAction(actionEvent -> {
                            try {
                                this.onReturn(borrow);
                            } catch (IOException e) {
                                System.out.println("error");
                            }
                        });
                        pane.getChildren().add(returnButton);
                        break;
                    case ArtItem.pendingBorrow:


                        status.setText("THIS ITEM IS REQUESTED");
                        review.setText("Review");
                        review.setStyle("-fx-background-color: #4bc565");
                        review.setOnAction(actionEvent -> {
                            try {
                                this.onReview(actionEvent, borrow);
                            } catch (IOException e) {
                                System.out.println("error");
                            }
                        });
                        pane.getChildren().add(review);
                        break;
                    default:
                        status.setText("In Gallery");
                        break;
                }

                final Pane spacer = new Pane();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                root.getChildren().addAll(review,spacer);
                pane.getChildren().addAll(root,status);

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

    private VBox createStuff(int index) {
        createImageView(index);
        return new VBox();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.createImageView(0);
        pagination.setMaxPageIndicatorCount(Math.min((artItemList.size() / 6) + 1, 5));
        pagination.setPageCount(artItemList.size() / 6 + 1);
        pagination.setPageFactory(this::createStuff);
    }
}
