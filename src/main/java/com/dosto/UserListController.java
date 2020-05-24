package com.dosto;

import com.dosto.models.User;
import com.dosto.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class UserListController implements Initializable {
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> username;
    @FXML private TableColumn<User, String> role;
    @FXML private TableColumn<User, String> lastName;
    @FXML private TableColumn<User, String> firstName;
    @FXML private TableColumn<User, String> email;

    private ObservableList<User> observableUsers = FXCollections.observableArrayList(UserService.getUsers());

    @FXML private void onCreateUser() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-user-form.fxml"));
        Parent parent=loader.load();
        CreateUserFormController userFormController = loader.getController();
        userFormController.setAppMainObservableList(observableUsers);
        Scene scene= new Scene(parent,600,350);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
//        Dialog<User> dialog = new Dialog<>();
//        dialog.setTitle("Aiurea");
//        dialog.setHeaderText("This is a custom dialog. Enter info and \n" +
//                "press Okay (or click title bar 'X' for cancel).");
//        dialog.setResizable(true);
//
//        Label label1 = new Label("Name: ");
//        Label label2 = new Label("Phone: ");
//        TextField text1 = new TextField();
//        TextField text2 = new TextField();
//
//        GridPane grid = new GridPane();
//        grid.add(label1, 1, 1);
//        grid.add(text1, 2, 1);
//        grid.add(label2, 1, 2);
//        grid.add(text2, 2, 2);
//        dialog.getDialogPane().setContent(grid);
//
//        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
//
//        dialog.setResultConverter(new Callback<>() {
//            @Override
//            public User call(ButtonType b) {
//
//                if (b == buttonTypeOk) {
//
//                    return new User(text1.getText(), text2.getText());
//                }
//
//                return null;
//            }
//        });
//
//        Optional<User> result = dialog.showAndWait();
//
//        if (result.isPresent()) {
//
//         //   actionStatus.setText("Result: " + result.get());
//            System.out.println("sal");
//        }
    }


    public UserListController(){
        System.out.println("User Constructed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable.setItems(observableUsers);



    }
}
