/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import service.UserServiceRedisImpl;
import util.AppBarIcon;

import java.io.IOException;

public class LoginScreenFormController {
    private final UserServiceRedisImpl userServiceRedis = new UserServiceRedisImpl();
    public JFXButton btnLogin;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public AnchorPane root;

    private static String loggedInUser = "";
    private static String loggedInUserName = "";

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String loggedInUser) {
        LoginScreenFormController.loggedInUser = loggedInUser;
    }

    public static String getloggedInUserName() {
        return loggedInUserName;
    }

    public static void setloggedInUserName(String loggedInUserName) {
        LoginScreenFormController.loggedInUserName = loggedInUserName;
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) throws IOException {
        if (!(txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty())){
            if (userServiceRedis.authenticate(txtUserName.getText(), txtPassword.getText())) {
                setLoggedInUser(txtUserName.getText());
                User user = userServiceRedis.findUser(txtUserName.getText());
                String[] name = user.getFullname().split(" ");
                loggedInUserName = name[0];
                initializeUI();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Login credentials").showAndWait();
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Username/password cannot be empty").showAndWait();
            txtUserName.requestFocus();
        }
    }

    private void initializeUI() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        MainFormController ctrl = fxmlLoader.getController();
        ctrl.navigate("Student Management System", "/view/HomeForm.fxml", AppBarIcon.NAV_ICON_HOME);
        mainScene.setUserData(ctrl);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Student Payment Management System");
        primaryStage.show();
        primaryStage.centerOnScreen();

        Platform.runLater(() -> {
            ((Stage) (btnLogin.getScene().getWindow())).close();
        });
    }

}
