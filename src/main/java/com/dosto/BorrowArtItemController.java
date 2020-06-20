package com.dosto;

import com.dosto.components.ComboBoxItemWrap;
import com.dosto.models.ArtItem;
import com.dosto.models.Borrow;
import com.dosto.models.User;
import com.dosto.services.ArtItemService;
import com.dosto.services.BorrowService;
import com.dosto.services.GlobalVars;
import com.dosto.services.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BorrowArtItemController implements Initializable {
    @FXML private AnchorPane selectCombo;
    @FXML private JFXComboBox<String> userCombo;
    @FXML private ComboBox<ComboBoxItemWrap<ArtItem>> itemsCombo;
    @FXML private Text requestedBy;
    @FXML private JFXButton rejectButton;
    @FXML private JFXButton submitButton;

    private Borrow borrow;
    private final ObservableList<User> users = FXCollections.observableArrayList(UserService.getUsers());

    @FXML public void onReject(ActionEvent event){
        if(borrow != null){
            BorrowService.rejectBorrow(borrow);
            this.closeStage(event);
        }
    }
    @FXML public void onSubmit(ActionEvent event){
        List<ArtItem> artItems = new ArrayList<>();
        itemsCombo.getItems().filtered(ComboBoxItemWrap::getCheck).forEach(item -> artItems.add(item.getItem()));
        if(borrow == null)
            BorrowService.createBorrow(artItems,GlobalVars.loggedUser.getUsername());
        else{
            if(borrow.getStatus().equals(Borrow.pendingStatus))
                BorrowService.updateBorrow(borrow, artItems);
            else if(borrow.getStatus().equals(Borrow.borrowedStatus))
                BorrowService.returnBorrowItems(borrow,artItems);
        }
        this.closeStage(event);
    }

    void setReturnBorrow(Borrow borrow){
        System.out.println(borrow.getArtItems());
        this.borrow=borrow;
        submitButton.setText("Return items");

        userCombo.setVisible(false);

        ObservableList<ComboBoxItemWrap<ArtItem>> values = FXCollections.observableArrayList(
                ArtItemService.getArtItemsByOwnerName(borrow.getCreator()).
                        stream().filter(artItem -> artItem.getStatus().equals(ArtItem.borrowedStatus)).map(ComboBoxItemWrap::new).collect(Collectors.toList()));
        itemsCombo.setItems(values);

        for (ComboBoxItemWrap<ArtItem> item : itemsCombo.getItems()) {
            if(borrow.getArtItems().contains(item.getItem().getId())){
                item.setCheck(true);
            }
        }

        StringBuilder sb = new StringBuilder();
        itemsCombo.getItems().filtered(Objects::nonNull).filtered(ComboBoxItemWrap::getCheck).forEach(p -> {
            sb.append("; ").append(p.getItem());
        });
        final String string = sb.toString();
        itemsCombo.setPromptText(string.substring(Integer.min(2, string.length())));
    }
    /**
     *
     * @param borrow this will only be used by the logged user , so please be careful with the usage
     * the only use-case for this is to accept the borrow
     *
     */
    void setBorrow(Borrow borrow){
        this.borrow = borrow;

        rejectButton.setVisible(true);
        rejectButton.setDisable(false);
        submitButton.setText("Accept");
        // set default user
         userCombo.setVisible(false);
        ObservableList<ComboBoxItemWrap<ArtItem>> values = FXCollections.observableArrayList(
                ArtItemService.getArtItemsByOwnerName(GlobalVars.loggedUser.getUsername()).
                        stream().filter(artItem -> artItem.getStatus().equals(ArtItem.pendingBorrow)).map(ComboBoxItemWrap::new).collect(Collectors.toList()));
        itemsCombo.setItems(values);

        for (ComboBoxItemWrap<ArtItem> item : itemsCombo.getItems()) {
            if(borrow.getArtItems().contains(item.getItem().getId())){
                item.setCheck(true);
            }
        }

        StringBuilder sb = new StringBuilder();
        itemsCombo.getItems().filtered(Objects::nonNull).filtered(ComboBoxItemWrap::getCheck).forEach(p -> {
            sb.append("; ").append(p.getItem());
        });
        final String string = sb.toString();
        itemsCombo.setPromptText(string.substring(Integer.min(2, string.length())));

        requestedBy.setText("This item was requested by " + borrow.getCreator());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> userList =  users.stream().filter(user -> !user.getRole().equals("admin") && !user.getUsername().equals(GlobalVars.loggedUser.getUsername())).map(User::getUsername).collect(Collectors.toList());
        userCombo.setItems(FXCollections.observableArrayList(userList));
        userCombo.getSelectionModel().selectedItemProperty().addListener((options , oldValue, newValue) -> {
            if(borrow == null){
                ObservableList<ComboBoxItemWrap<ArtItem>> values = FXCollections.observableArrayList(
                        ArtItemService.getArtItemsByOwnerName(newValue).
                                stream().filter(item -> item.getStatus().equals(ArtItem.inGalleryStatus)).map(ComboBoxItemWrap::new).collect(Collectors.toList()));
                itemsCombo.setItems(values);
            }
        });

        itemsCombo.setCellFactory(c -> {
            ListCell<ComboBoxItemWrap<ArtItem>> cell = new ListCell<>() {
                @Override
                protected void updateItem(ComboBoxItemWrap<ArtItem> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        VBox box = new VBox();
                        final CheckBox cb = new CheckBox(item.toString());
                        ImageView imageView = new ImageView();
                        Image image = item.getItem().getImage();
                        imageView.setImage(image);
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        cb.selectedProperty().bind(item.checkProperty());
                        box.getChildren().addAll(cb, imageView);
                        setGraphic(box);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                cell.getItem().checkProperty().set(!cell.getItem().checkProperty().get());
                StringBuilder sb = new StringBuilder();
                itemsCombo.getItems().filtered(Objects::nonNull).filtered(ComboBoxItemWrap::getCheck).forEach(p -> {
                    sb.append("; ").append(p.getItem());
                });
                final String string = sb.toString();
                itemsCombo.setPromptText(string.substring(Integer.min(2, string.length())));
            });

            return cell;
        });
        itemsCombo.setVisibleRowCount(4);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
