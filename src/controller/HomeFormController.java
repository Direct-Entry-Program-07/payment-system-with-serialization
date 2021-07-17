/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.AppBarIcon;
import util.MaterialUI;

import java.util.Date;

public class HomeFormController {

    public TableView tblDashboard;
    public JFXButton btnManageStudents;
    public JFXButton btnManageCourses;
    public JFXButton btnManagePayments;
    public JFXButton btnManageUsers;
    public JFXButton btnViewPayments;
    public Label lblDate;
    public JFXDatePicker pckrDate;
    public TextField txtQuery;
    public Label lblTime;

    public void initialize() {

        MaterialUI.paintTextFields(txtQuery);

        GetTime();
        GetDate();

        tblDashboard.getItems().add(new Object());
        tblDashboard.getItems().add(new Object());
        tblDashboard.getItems().add(new Object());
        tblDashboard.getItems().add(new Object());

    }

    private void GetDate() {
        String date = String.format("%1$tY-%1$tm-%1$td", new Date());
        lblDate.setText(date);
    }

    private void GetTime() {

        MainFormController mainFormController = new MainFormController();
        mainFormController.setSessionStatus(1);

        Task task = new Task(){

            @Override
            protected Object call() throws Exception {
                while (true){
                    String time = String.format("%tT", new Date());
                    try{
                        Thread.sleep(900);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{
                        lblTime.setText(time);
                    });
                }
            }
        };
        Thread timer = new Thread(task);
        timer.setDaemon(true);
        timer.start();
    }

    public void btnManageStudents_OnAction(ActionEvent actionEvent) {
        navigate("Manage Students", "/view/ManageStudentsForm.fxml");
    }

    public void btnManageStudents_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            navigate("Manage Students", "/view/ManageStudentsForm.fxml");
        }
    }

    public void btnManageCourses_OnAction(ActionEvent actionEvent) {
            navigate("Manage Course Details", "/view/ManageCoursesForm.fxml");
    }

    public void btnManageCourses_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            navigate("Manage Course Details", "/view/ManageCoursesForm.fxml");
        }
    }

    public void btnManagePayments_OnAction(ActionEvent actionEvent) {
        navigate("Manage Payment Details", "/view/ManagePaymentsForm.fxml");
    }

    public void btnManagePayments_OnKyeReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            navigate("Manage Payment Details", "/view/ManagePaymentsForm.fxml");
        }
    }

    public void btnManageUsers_OnAction(ActionEvent actionEvent) {
       // System.out.println(LoginScreenFormController.getLoggedInUser());
        if (LoginScreenFormController.getLoggedInUser().contains("admin") || LoginScreenFormController.getLoggedInUser().contains("root")){
        navigate("Manage Users", "/view/ManageUsersForm.fxml");
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Access denied! are you admin?" , ButtonType.OK).showAndWait();
        }
    }

    public void btnManageUsers_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            navigate("Manage Users", "/view/ManageUsersForm.fxml");
        }
    }

    public void btnViewPayments_OnAction(ActionEvent actionEvent) {
        navigate("View All Payments", "/view/ViewAllPaymentsForm.fxml");
    }

    public void btnViewPayments_OnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            navigate("View All Payments", "/view/ViewAllPaymentsForm.fxml");
        }
    }

    private void navigate(String title, String url) {
        MainFormController ctrl = (MainFormController) tblDashboard.getScene().getUserData();
        ctrl.navigate(title, url, AppBarIcon.NAV_ICON_BACK, () -> {
            ctrl.navigate("Student Payment Management System", "/view/HomeForm.fxml", AppBarIcon.NAV_ICON_HOME);
        });
    }
}
