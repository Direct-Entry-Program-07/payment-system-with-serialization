/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Student;
import model.StudentTM;
import service.StudentService;
import service.exception.FailedOperationException;
import util.AppBarIcon;
import util.MaterialUI;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ManageStudentsFormController {
    public TextField txtQuery;
    public TableView<StudentTM> tblStudents;
    public JFXButton btnAddNewStudent;
    private final StudentService studentService = new StudentService();

    public void initialize() {

        MaterialUI.paintTextFields(txtQuery);

        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudents.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        tblStudents.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        tblStudents.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("course"));
        TableColumn<StudentTM, HBox> operatorCol = (TableColumn<StudentTM, HBox>) tblStudents.getColumns().get(6);

        operatorCol.setCellValueFactory(param -> {
            ImageView imgEdit = new ImageView("/view/assets/icons/Edit.png");
            ImageView imgTrash = new ImageView("/view/assets/icons/Trash.png");

            imgEdit.getStyleClass().add("action-icons");
            imgTrash.getStyleClass().add("action-icons");

            imgEdit.setOnMouseClicked(event -> updateStudent(param.getValue()));
            imgTrash.setOnMouseClicked(event -> deleteStudent(param.getValue()));

            return new ReadOnlyObjectWrapper<>(new HBox(10, imgEdit, imgTrash));
        });

        txtQuery.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadAllStudents(newValue);
            }
        });

        tblStudents.itemsProperty().addListener(new ChangeListener<ObservableList<StudentTM>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<StudentTM>> observable, ObservableList<StudentTM> oldValue, ObservableList<StudentTM> newValue) {
                if (newValue != null){
                    //System.out.println("changed");
                    tblStudents.refresh();
                }
            }
        });


        loadAllStudents("");
    }

    private void loadAllStudents(String query) {
        tblStudents.getItems().clear();

        for (Student student : studentService.findStudents(query)) {
            tblStudents.getItems().add(new StudentTM(
                    student.getFullName(),
                    student.getNic(),
                    student.getAddress(),
                    student.getContactNumber(),
                    student.getEmailAddress(),
                    student.getCourseId()));
        }
    }

    public void btnAddNewStudent_OnAction(ActionEvent actionEvent) {
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
            secondaryStage.setTitle("Add new Student");

            ctrl.navigate("Add new Student", "/view/AddStudentForm.fxml", AppBarIcon.NAV_ICON_NONE);

            secondaryStage.showAndWait();
            tblStudents.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent(StudentTM tm) {
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
            secondaryStage.setTitle("Update Student");
            secondaryStage.centerOnScreen();
            ctrl.navigate("Update Student", "/view/AddStudentForm.fxml", AppBarIcon.NAV_ICON_NONE, null, tm);

            secondaryStage.showAndWait();
            tblStudents.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent(StudentTM tm) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this student?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                studentService.deleteStudent(tm.getNic());
                tblStudents.getItems().remove(tm);
            }
        } catch (RuntimeException | FailedOperationException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item", ButtonType.OK).show();
        }
    }

    public void tblStudents_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.DELETE){
            deleteStudent(tblStudents.getSelectionModel().getSelectedItem());
        }else if (keyEvent.getCode() == KeyCode.ENTER){
            updateStudent(tblStudents.getSelectionModel().getSelectedItem());
        }
    }
}
