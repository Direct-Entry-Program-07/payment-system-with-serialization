/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import service.UserServiceRedisImpl;
import util.AppBarIcon;
import util.MaterialUI;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class ManageUsersFormController {

    public TextField txtQuery;
    public TableView<UserTM> tblUsers;
    public JFXButton btnAddUser;

    private final UserServiceRedisImpl userService = new UserServiceRedisImpl();

    public void initialize(){

        MaterialUI.paintTextFields(txtQuery);

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("fullname"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("userType"));
        /*TableColumn<UserTM, ComboBox> userTypeCombo = (TableColumn<UserTM, ComboBox>) tblUsers.getColumns().get(1);
        userTypeCombo.setCellValueFactory(param -> {
            ComboBox<String> cmbBatch = new ComboBox();
            cmbBatch.setPrefWidth(tblUsers.getColumns().get(1).getWidth());
            cmbBatch.setPrefHeight(40);

            // batchId.setItems(batchIdOptions);

            tblUsers.getColumns().get(1).widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    cmbBatch.setPrefWidth((double) newValue);
                }
            });

            return new ReadOnlyObjectWrapper<>(cmbBatch);
        });*/

        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("username"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        tblUsers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblUsers.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        TableColumn<UserTM, HBox> operatorCol = (TableColumn<UserTM, HBox>) tblUsers.getColumns().get(7);

        operatorCol.setCellValueFactory(param -> {
            ImageView imgEdit = new ImageView("/view/assets/icons/Edit.png");
            ImageView imgTrash = new ImageView("/view/assets/icons/Trash.png");

            imgEdit.getStyleClass().add("action-icons");
            imgTrash.getStyleClass().add("action-icons");

            imgEdit.setOnMouseClicked(event -> updateUser(param.getValue()));
            imgTrash.setOnMouseClicked(event -> deleteUser(param.getValue()));

            return new ReadOnlyObjectWrapper<>(new HBox(10, imgEdit, imgTrash));
        });

        LoadAllUsers("");

    }

    private void deleteUser(UserTM tm) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this user?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                userService.deleteUser(tm.getUsername());
                tblUsers.getItems().remove(tm);
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item", ButtonType.OK).show();
        }
    }

    private void updateUser(UserTM tm) {
        try {
            Stage secondaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/SecondaryMainForm.fxml"));
            Scene secondaryScene = new Scene(loader.load());
            SecondaryMainFormController ctrl = loader.getController();

            secondaryStage.setScene(secondaryScene);
            secondaryScene.setFill(Color.TRANSPARENT);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(txtQuery.getScene().getWindow());
            secondaryStage.setTitle("Update User");
            secondaryStage.centerOnScreen();
            ctrl.navigate("Update User", "/view/AddUserForm.fxml", AppBarIcon.NAV_ICON_NONE, null, tm);

            secondaryStage.showAndWait();
            tblUsers.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllUsers(String query) {
        tblUsers.getItems().clear();

        for (User user : userService.findUsers(query)) {

            tblUsers.getItems().add(new UserTM(
                    user.getFullname(),
                    user.getUserType(),
                    user.getUsername(),
                    user.getAddress(),
                    user.getContactNumber(),
                    user.getEmailAddress(),
                    user.getJoinedDate()
            ));
        }
    }

    public void btnAddUser_OnAction(ActionEvent actionEvent) {
        try {
            Stage secondaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/SecondaryMainForm.fxml"));
            Scene secondaryScene = new Scene(loader.load());
            SecondaryMainFormController ctrl = loader.getController();

            secondaryStage.setScene(secondaryScene);
            secondaryScene.setFill(Color.TRANSPARENT);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(txtQuery.getScene().getWindow());
            secondaryStage.setTitle("Add new User");

            ctrl.navigate("Add new User", "/view/AddUserForm.fxml", AppBarIcon.NAV_ICON_NONE);

            secondaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
