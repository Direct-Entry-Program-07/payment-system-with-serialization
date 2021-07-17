/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import util.AppBarIcon;
import util.NavActionListener;

import java.io.IOException;

public class SecondaryMainFormController {
    public AnchorPane pneAppBar;
    public ImageView imgClose;
    public Label lblTitle;
    public AnchorPane pneStage;

    private double xMpusePos;
    private double yMpusePos;

    public void initialize() {

        initWindow();
    }

    public void navigate(String title, String url, AppBarIcon icon){

        navigate(title, url, icon, null);
    }


    public void navigate(String title, String url, AppBarIcon icon, NavActionListener navActionListener) {
        navigate(title,url,icon,navActionListener,null);
    }

    public void navigate(String title, String url, AppBarIcon icon, NavActionListener navActionListener, Object data){

        try {
            Stage primaryStage = ((Stage) pneStage.getScene().getWindow());
            AnchorPane root= FXMLLoader.load(this.getClass().getResource(url));
            root.setUserData(data);
            pneStage.getChildren().clear();
            FadeTransition ft = new FadeTransition(Duration.millis(500), pneStage);
            ft.setFromValue(0);
            ft.setToValue(1);

            pneStage.getChildren().add(root);
            ft.play();
            lblTitle.setText(title);

            Platform.runLater(() -> {
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWindow() {
        lblTitle.setMouseTransparent(true);

        Platform.runLater(() -> {
            lblTitle.setText(((Stage) (imgClose.getScene().getWindow())).getTitle());
        });

        pneAppBar.setOnMousePressed(event -> {
            xMpusePos = event.getX();
            yMpusePos = event.getY();
        });

        pneAppBar.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                Window mainWindow = imgClose.getScene().getWindow();
                mainWindow.setX(event.getScreenX() - xMpusePos);
                mainWindow.setY(event.getScreenY() - yMpusePos);
            }
        });

        imgClose.setOnMouseEntered(event -> imgClose.setImage(new Image("/view/assets/icons/close-hover.png")));
        imgClose.setOnMouseExited(event -> imgClose.setImage(new Image("/view/assets/icons/close.png")));
        imgClose.setOnMouseClicked(event -> ((Stage) (imgClose.getScene().getWindow())).close());
    }

}
