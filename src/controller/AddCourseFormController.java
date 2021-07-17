/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Batch;
import model.Course;
import model.CourseTM;
import service.CourseService;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

import static util.ValidationUtil.isValidDate;

public class AddCourseFormController {
    private final CourseService courseService = new CourseService();

    public JFXButton btnSave;
    public JFXButton btnCancel;
    public AnchorPane root;
    public TextField txtCourseId;
    public TextField txtCourseName;
    public TextField txtBatchId;
    public JFXButton btnCreateANewBatch;
    public TextField txtNoOfStudents;
    public JFXDatePicker pckrCommencingDate;
    public TextArea txtNote;

    public void initialize() {
        txtBatchId.setEditable(false);
        Platform.runLater(() -> {

            if (root.getUserData() != null) {
                CourseTM tm = (CourseTM) root.getUserData();
                Course course = null;
                try {
                    course = courseService.findCourse(tm.getCourseID());
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something terribly wrong, Please contact DEP)!", ButtonType.OK).show();
                }

                txtCourseId.setEditable(false);
              //  txtCourseId.setText(course.getCourseID()+"-"+course.getBatchID());
                txtCourseName.setText(course.getCourseName());
                txtBatchId.setEditable(false);
                btnCreateANewBatch.setDisable(true);
              //  txtBatchId.setText(String.valueOf(course.getBatchID()));
                txtNoOfStudents.setText(String.valueOf(course.getNoOfStudentsForTheBatch()));
                pckrCommencingDate.setValue(LocalDate.parse(pckrCommencingDate.getValue().toString()));
                txtNote.setText(course.getNote());

                btnSave.setText("Update Course");
            }
        });

        txtCourseId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue && !txtCourseId.getText().equals("")){

                    if (courseService.isCourseExists(txtCourseId.getText(), txtBatchId.getText())){
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR, "Course Already exists. Do you like to add new branch instead of creating new course?", ButtonType.YES,ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            int newBatch = Integer.parseInt(txtBatchId.getText()) + 1;
                            txtBatchId.setText(String.valueOf(newBatch));
                            //System.out.println(txtBatchId.getText());

                            txtCourseName.setText(courseService.getCourseNameUsingId(txtCourseId.getText()));
                            txtNoOfStudents.requestFocus();

                        }else {
                            txtCourseId.requestFocus();
                            return;
                        }
                    }
                }
            }
        });


    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        if (!isValidated()){
            return;
        }

        try {
            Course course = new Course(
                    txtCourseId.getText(),
                    txtCourseName.getText(),
                    new Batch(txtBatchId.getText(), Integer.parseInt(txtNoOfStudents.getText()), LocalDate.parse(pckrCommencingDate.getValue().toString()), txtNote.getText())
            );



            if (courseService.isCourseExists(txtCourseId.getText(), txtBatchId.getText())){
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR, "Course Already exists. Do you like to add new branch instead of creating new course?", ButtonType.YES,ButtonType.NO).showAndWait();
                if (buttonType.get() == ButtonType.YES){
                    int newBatch = Integer.parseInt(txtBatchId.getText()) + 1;
                    txtBatchId.setText(String.valueOf(newBatch));
                    //System.out.println(txtBatchId.getText());

                }else {
                    txtCourseId.requestFocus();
                    return;
                }
            }

            if (btnSave.getText().equals("Save Course")) {
                courseService.saveCourse(course);
            } else {

                CourseTM tm = (CourseTM) root.getUserData();
                tm.setCourseName(txtCourseName.getText());
                tm.setSelectedBatch(new Batch(txtBatchId.getText(), Integer.parseInt(txtNoOfStudents.getText()), LocalDate.parse(pckrCommencingDate.getValue().toString()), txtNote.getText()));
                courseService.updateCourse(course);
            }
            new Alert(Alert.AlertType.NONE, "Course has been saved successfully", ButtonType.OK).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
        Window window = btnCancel.getScene().getWindow();
        ((Stage) window).close();
    }

    public void btnCreateANewBatch_OnAction(ActionEvent actionEvent) {
        txtBatchId.setText(String.valueOf(Integer.parseInt(txtBatchId.getText()) + 1));
        btnCreateANewBatch.setDisable(true);
    }

    public boolean isValidated(){
        String courseId = txtCourseId.getText();
        String courseName = txtCourseName.getText();
        String batchId = txtBatchId.getText();
        String noOfStudentsForTheBatch = txtNoOfStudents.getText();
        String commencingDate = "1999-09-09";/////////////////+++++++++++++++////////////////
        String note = txtNote.getText();

        Pattern courseIdPattern = Pattern.compile("[A-Za-z]+");
        Pattern courseNamePattern = Pattern.compile("[A-Za-z ]+");
        Pattern batchIdPattern = Pattern.compile("\\d{1,2}");
        Pattern noStudentPattern = Pattern.compile("\\d{1,3}");

        if (!(courseIdPattern.matcher(courseId).matches())){
            new Alert(Alert.AlertType.ERROR, "Invalid Course ID").show();
            txtCourseId.requestFocus();
            return false;
        }else if (!(courseNamePattern.matcher(courseName).matches())){
            new Alert(Alert.AlertType.ERROR, "Invalid Course Name").show();
            txtCourseName.requestFocus();
            return false;
        }else if (!(batchIdPattern.matcher(batchId).matches())){
            new Alert(Alert.AlertType.ERROR, "Invalid Batch ID").show();
            txtBatchId.requestFocus();
            return false;
        }else if (!(noStudentPattern.matcher(noOfStudentsForTheBatch).matches())){
            new Alert(Alert.AlertType.ERROR, "Invalid Number of Students for the batch").show();
            txtNoOfStudents.requestFocus();
            return false;
        }else if (!isValidDate(commencingDate)) {
            new Alert(Alert.AlertType.ERROR, "Invalid date").show();
            pckrCommencingDate.requestFocus();
        }
        return true;
    }
}
