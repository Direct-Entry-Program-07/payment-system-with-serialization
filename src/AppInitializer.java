/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import controller.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.AppBarIcon;
import util.JedisClient;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {

        /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            JedisClient.getInstance().getClient().shutdown();
        }));*/

        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        MainFormController ctrl = fxmlLoader.getController();
        ctrl.navigate("Student Management System","/view/HomeForm.fxml", AppBarIcon.NAV_ICON_HOME);
        mainScene.setUserData(ctrl);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Student Payment Management System");
        primaryStage.show();
        primaryStage.centerOnScreen();

     /*   AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/SplashScreenForm.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();*/
    }
}
