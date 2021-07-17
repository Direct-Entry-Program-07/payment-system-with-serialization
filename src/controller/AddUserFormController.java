/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.User;
import model.UserTM;
import org.apache.commons.codec.digest.DigestUtils;
import service.UserService;
import service.UserServiceRedisImpl;

import java.lang.invoke.SwitchPoint;
import java.util.regex.Pattern;

public class AddUserFormController {
    public JFXButton btnSave;
    public JFXButton btnCancel;
    public ComboBox cmbUserType;
    public TextField txtUsername;
    public TextField txtFullname;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public AnchorPane root;
    public TextArea txtAddress;
    public TextField txtContactNumber;
    public TextField txtEmail;

    public UserService userService = new UserService();
    public ObservableList<String> usertypes = FXCollections.observableArrayList();

    public void initialize() {
        if (LoginScreenFormController.getLoggedInUser().contains("root")) {
            usertypes.add("root");
            usertypes.add("admin");
            usertypes.add("user");
        } else if (LoginScreenFormController.getLoggedInUser().contains("admin")) {
            usertypes.add("admin");
            usertypes.add("user");
        }
        Platform.runLater(() -> {
            cmbUserType.setPromptText("Select user type");
            cmbUserType.setItems(usertypes);

            if (root.getUserData() != null) {
                txtUsername.setEditable(false);
               // txtPassword.setEditable(false);
                txtPassword.setDisable(true);
                txtConfirmPassword.setDisable(true);

                UserTM tm = (UserTM) root.getUserData();
                User user = userService.findUser(tm.getUsername());
               // System.out.println(user);

                // cmbUserType.setItems();
                switch (user.getUserType()){
                    case ("root"): cmbUserType.getSelectionModel().select(0);
                    break;

                    case ("admin"): cmbUserType.getSelectionModel().select(1);
                    break;

                    case ("user"): cmbUserType.getSelectionModel().select(2);
                    break;
                }

                txtUsername.setText(user.getUsername());
                txtFullname.setText(user.getFullname());
                txtAddress.setText(user.getAddress());
                txtContactNumber.setText(user.getContactNumber());
                txtEmail.setText(user.getEmailAddress());

                btnSave.setText("Update User");
            }
        });

        cmbUserType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               if (newValue == "root"){
                   txtUsername.setText("root");
               }else if (newValue == "admin"){
                   txtUsername.setText("admin");
               }else if (newValue == "user"){
                   txtUsername.setText("user");
               }

              //  System.out.println(cmbUserType.getValue());
            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        try {
            if (!isValidated()) {
                return;
            }

            String pwdHash = DigestUtils.sha256Hex(txtPassword.getText());
           //System.out.println(pwdHash);
            User user = new User((String) cmbUserType.getValue(),
                    txtUsername.getText(),
                    txtFullname.getText(),
                    pwdHash,
                    txtAddress.getText(),
                   txtContactNumber.getText(),
                    txtEmail.getText());

            if (btnSave.getText().equals("Save User")) {
                userService.saveUser(user);
            } else {
               /* UserTM tm = (UserTM) root.getUserData();
                tm.setUserType((String) cmbUserType.getValue());
                tm.setFullname(txtFullname.getText());
                tm.setContactNumber(txtContactNumber.getText());
                tm.setEmailAddress(txtEmailAddress.getText());
                tm.setCourse(cmbCourseID.getValue() + "-" + cmbBatchID.getValue());
                userService.updateUser(user);*/
            }
            new Alert(Alert.AlertType.NONE, "User has been saved successfully", ButtonType.OK).show();

        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the user", ButtonType.OK).show();
        }
    }

    private boolean isValidated() {
        String username = txtUsername.getText();
        String fullname = txtFullname.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String address = txtAddress.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();

        Pattern fullNamePattern = Pattern.compile("[A-Za-z]{3,}[A-Za-z ]*");
        Pattern mobileNumberPattern = Pattern.compile("\\d{3}-\\d{7}$");
        Pattern addressPattern = Pattern.compile("[A-Za-z0-9 :,.-/\\\\]+");
        Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Pattern passwordPattern = Pattern.compile("[A-Za-z0-9]{4,}");

        if (cmbUserType.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select user type").show();
            cmbUserType.requestFocus();
            return false;
        } else if (username.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            txtUsername.requestFocus();
            return false;
        } else if (!(fullNamePattern.matcher(fullname).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtFullname.requestFocus();
            return false;
        } else if (!(passwordPattern.matcher(password).matches()) && btnSave.getText().equals("Save User")) {
            new Alert(Alert.AlertType.ERROR, "Invalid password").show();
            txtPassword.requestFocus();
            return false;
        } else if(!confirmPassword.equals(password) && btnSave.getText().equals("Save User")){
            new Alert(Alert.AlertType.ERROR, "password not matched").show();
            txtConfirmPassword.requestFocus();
            return false;
        } else if (!(addressPattern.matcher(address).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid address").show();
            txtAddress.requestFocus();
            return false;
        } else if (!(mobileNumberPattern.matcher(contactNumber).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number").show();
            txtContactNumber.requestFocus();
            return false;
        } else if (!(emailPattern.matcher(email).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email").show();
            txtEmail.requestFocus();
            return false;
        }

        return true;


    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
        Window window = btnCancel.getScene().getWindow();
        ((Stage) window).close();
    }
}
