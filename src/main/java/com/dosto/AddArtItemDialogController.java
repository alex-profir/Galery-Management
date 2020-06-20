package com.dosto;

import com.dosto.models.ArtItem;
import com.dosto.services.ArtItemService;
import com.dosto.services.GlobalVars;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class AddArtItemDialogController {
    @FXML private JFXTextField name;
    @FXML private JFXTextField artist;
    @FXML private JFXTextArea description;
    @FXML private Label errorLabel;
    @FXML private ImageView testImage;
    @FXML private String imageString = null;
    private ObservableList<ArtItem> appMainObservableList;
    @FXML private void onSubmit(ActionEvent event){
        if(imageString == null) {
            errorLabel.setText("Please upload an image");
            return;
        }
        if(name.getText().trim().length()==0) {
            errorLabel.setText("Name required");
            return;
        }
        if(artist.getText().trim().length()==0) {
            errorLabel.setText("Artist required");
            return;
        }
        if(description.getText().trim().length()==0) {
            errorLabel.setText("Description required");
            return;
        }
        ArtItem artItem = new ArtItem(name.getText(),artist.getText(),description.getText(),imageString,ArtItem.pendingStatus);
        ArtItemService.addArtItem(artItem);
       // appMainObservableList.add(artItem);
        this.closeStage(event);
    }
    @FXML private void onUploadImage(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null)
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            this.imageString=encodedString;
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            Image img = new Image(new ByteArrayInputStream(decodedBytes));
            testImage.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void setAppMainObservableList(ObservableList<ArtItem> tvObservableList) {
        this.appMainObservableList = tvObservableList;
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
