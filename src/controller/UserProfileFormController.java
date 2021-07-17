/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UserProfileFormController {
    public AnchorPane root;
    public TextField txtUsername;
    public TextField txtFullname;
    public TextArea txtAddress;
    public TextField txtContactNumber;
    public TextField txtEmail;
    public ImageView imgProfilePic;
    public JFXButton btnChangePic;
    public JFXButton btnChangePassword;

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
    }

    public void btnChangePic_OnAction(ActionEvent actionEvent) {
    }

    public void btnChangePassword_OnAcion(ActionEvent actionEvent) {

    }
}
