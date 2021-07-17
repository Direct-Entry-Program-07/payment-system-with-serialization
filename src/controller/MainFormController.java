/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import model.Student;
import model.StudentTM;
import util.AppBarIcon;
import util.NavActionListener;

import java.io.IOException;

public class MainFormController {

    public Label lblName;
    public ImageView imgUserProf;
    public AnchorPane pneUserProfile;
    private int sessionStatus = 0;

    public AnchorPane pneAppBar;
    public ImageView imgClose;
    public ImageView imgMinimize;
    public Label lblTitle;
    public ImageView imgNav;
    public AnchorPane pneStage;

    private double xMpusePos;
    private double yMpusePos;

    private AppBarIcon icon = AppBarIcon.NAV_ICON_NONE;
    private NavActionListener navActionListener = null;

    public void initialize() {

        initWindow();
    }

    public void navigate(String title, String url, AppBarIcon icon){

        navigate(title, url, icon, null);
    }

    public void navigate(String title, String url, AppBarIcon icon, NavActionListener navActionListener) {
        navigate(title, url, icon, navActionListener, null);
    }

    public void navigate(String title, String url, AppBarIcon icon, NavActionListener navActionListener, Object data){

        try {
            imgNav.setVisible(true);
            this.icon = icon;
            this.navActionListener = navActionListener;

            if (this.navActionListener == null) {
                imgNav.setCursor(Cursor.DEFAULT);
            } else {
                imgNav.setCursor(Cursor.HAND);
            }

            /* Let's set the icon*/
            switch (icon) {
                case NAV_ICON_NONE:
                    imgNav.setVisible(false);
                    imgNav.setUserData(null);
                    break;
                case NAV_ICON_HOME:
                    imgNav.setImage(new Image("/view/assets/icons/home.png"));
                    imgNav.setUserData(new Image("/view/assets/icons/home-hover.png"));
                    break;
                case NAV_ICON_BACK:
                    imgNav.setImage(new Image("/view/assets/icons/back.png"));
                    imgNav.setUserData(new Image("/view/assets/icons/back-hover.png"));
                    break;
            }

            Stage primaryStage = ((Stage) pneStage.getScene().getWindow());
            AnchorPane root = FXMLLoader.load(this.getClass().getResource(url));
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
        imgNav.setVisible(false);

        Platform.runLater(() -> {
            lblTitle.setText(((Stage) (imgClose.getScene().getWindow())).getTitle());
            lblName.setText("Welcome " + LoginScreenFormController.getloggedInUserName());
        });

        pneAppBar.setOnMousePressed(event -> {
            xMpusePos = event.getX();
            yMpusePos = event.getY();
        });

        pneAppBar.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                Window mainWindow = imgMinimize.getScene().getWindow();
                mainWindow.setX(event.getScreenX() - xMpusePos);
                mainWindow.setY(event.getScreenY() - yMpusePos);
            }
        });

        imgNav.setOnMouseEntered(event -> swapNavIcon());
        imgNav.setOnMouseExited(event -> swapNavIcon());
        imgNav.setOnMouseClicked(event -> {
            if (navActionListener != null) {
                navActionListener.handle();
            }
        });

        imgClose.setOnMouseEntered(event -> imgClose.setImage(new Image("/view/assets/icons/close-hover.png")));
        imgClose.setOnMouseExited(event -> imgClose.setImage(new Image("/view/assets/icons/close.png")));
        imgClose.setOnMouseClicked(event -> ((Stage) (imgClose.getScene().getWindow())).close());

        imgMinimize.setOnMouseEntered(event -> imgMinimize.setImage(new Image("/view/assets/icons/minimize-hover.png")));
        imgMinimize.setOnMouseExited(event -> imgMinimize.setImage(new Image("/view/assets/icons/minimize.png")));
        imgMinimize.setOnMouseClicked(event -> ((Stage) (imgClose.getScene().getWindow())).setIconified(true));

    }

    private void swapNavIcon() {
        if (icon != AppBarIcon.NAV_ICON_NONE && navActionListener != null) {
            Image temp = imgNav.getImage();
            imgNav.setImage((Image) imgNav.getUserData());
            imgNav.setUserData(temp);
        }
    }

    public int getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(int sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public void imgUserProf_MouseClicked(MouseEvent mouseEvent) {
    }

    public void pneUserProfile_OnMouseClicked(MouseEvent mouseEvent) {
        try {
            Stage secondaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/SecondaryMainForm.fxml"));
            Scene secondaryScene = new Scene(loader.load());
            SecondaryMainFormController ctrl = loader.getController();

            secondaryStage.setScene(secondaryScene);
            secondaryScene.setFill(Color.TRANSPARENT);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(imgNav.getScene().getWindow());
            secondaryStage.setTitle("User Profile");

            ctrl.navigate("User Profile", "/view/UserProfileForm.fxml", AppBarIcon.NAV_ICON_NONE);

            secondaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
