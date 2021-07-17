/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Batch;
import model.Course;
import model.CourseTM;
import service.CourseService;
import service.exception.NotFoundException;
import util.AppBarIcon;
import util.MaterialUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ManageCoursesFormController {

    private final CourseService courseService = new CourseService();
    public TextField txtQuery;
    public TableView<CourseTM> tblCourses;
    public JFXButton btnAddCourse;
    //ObservableList<String> cmbBatchIdOptions = FXCollections.observableArrayList();
    TableColumn<CourseTM, String> firstColumn;

    ObservableList<String> batchIdOptions = FXCollections.observableArrayList();


    public void initialize() {
        MaterialUI.paintTextFields(txtQuery);

        List<Batch> depBatches = new ArrayList<>();
        List<Batch> cmjdBatches = new ArrayList<>();
        List<Batch> rwadBatches = new ArrayList<>();
        List<Batch> absdBatches = new ArrayList<>();
        List<Batch> gdseBatches = new ArrayList<>();



        tblCourses.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblCourses.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("courseName"));
        TableColumn<CourseTM, ComboBox> batchIdCombo = (TableColumn<CourseTM, ComboBox>) tblCourses.getColumns().get(2);
        batchIdCombo.setCellValueFactory(param -> {
            ComboBox<Batch> cmbBatch = new ComboBox();
            cmbBatch.setPrefWidth(tblCourses.getColumns().get(2).getWidth());
            cmbBatch.setPrefHeight(40);

            // batchId.setItems(batchIdOptions);

            tblCourses.getColumns().get(2).widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    cmbBatch.setPrefWidth((double) newValue);
                }
            });

            switch (param.getValue().getCourseName()) {
                case "DEP":
                    cmbBatch.getItems().addAll(cmjdBatches);
                    break;
                case "CMJD":
                    cmbBatch.getItems().addAll(cmjdBatches);
                    break;
                case "GDSE":
                    cmbBatch.getItems().addAll(gdseBatches);
                    break;
                case "ABSD":
                    cmbBatch.getItems().addAll(absdBatches);
                    break;
                case "RWAD":
                    cmbBatch.getItems().addAll(rwadBatches);
                    break;
            }

            if (param.getValue().getSelectedBatch() != null) {
                cmbBatch.setValue(param.getValue().getSelectedBatch());
            }

            cmbBatch.valueProperty().addListener((observable, oldValue, newValue) -> {
                param.getValue().setSelectedBatch(newValue);
                tblCourses.refresh();
            });

            return new ReadOnlyObjectWrapper<>(cmbBatch);
        });

        tblCourses.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noOfStudentsForTheBatch"));
        tblCourses.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>(""));
        tblCourses.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("batchCommencingDate"));
        TableColumn<CourseTM, HBox> operateCol = (TableColumn<CourseTM, HBox>) tblCourses.getColumns().get(6);

        operateCol.setCellValueFactory(param -> {
            ImageView imgEdit = new ImageView("/view/assets/icons/Edit.png");
            ImageView imgTrash = new ImageView("/view/assets/icons/Trash.png");

            imgEdit.getStyleClass().add("action-icons");
            imgTrash.getStyleClass().add("action-icons");

            imgEdit.setOnMouseClicked(event -> updateCourse(param.getValue()));
            imgTrash.setOnMouseClicked(event -> deleteCourse(param.getValue()));

            return new ReadOnlyObjectWrapper<>(new HBox(10, imgEdit, imgTrash));
        });

        txtQuery.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                LoadAllCourses(newValue);
            }
        });

        LoadAllCourses("");


    }

    private void deleteCourse(CourseTM tm) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this student?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                courseService.deleteCourse(tm.getCourseID());
                tblCourses.getItems().remove(tm);
            }
        } catch (RuntimeException | NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item", ButtonType.OK).show();
        }
    }

    private void updateCourse(CourseTM tm) {
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
            secondaryStage.setTitle("Update Course");
            secondaryStage.centerOnScreen();
            ctrl.navigate("Update Course", "/view/AddCourseForm.fxml", AppBarIcon.NAV_ICON_NONE, null, tm);

            secondaryStage.showAndWait();
            tblCourses.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllCourses(String query) {

        tblCourses.getItems().clear();

        Set<String> allCourses = courseService.getAllCourses();

        for (Course course : courseService.findCourses(query)) {

            tblCourses.getItems().add(new CourseTM(
                    course.getCourseID(),
                    course.getCourseName(),
                    course.getSelectedBatch(),
                    course.getSelectedBatch().getStudentCount(),
                    course.getSelectedBatch().getCommencingDate(),
                    course.getSelectedBatch().getNote()
            ));
        }

        // System.out.println(tblCourses.toString());

//        checkAlreadyInTable();

       /* for (Course course : courseService.findCourses(query)) {

            int count = 0;
            // System.out.println(count);
            if (count != 0) {

                checkAlreadyInTable(course.getCourseID());
            }
            tblCourses.getItems().add(new CourseTM(
                    course.getCourseID(),
                    course.getCourseName(),
                    course.
                    course.getNoOfStudentsForTheBatch(),
                    course.getBatchCommencingDate(),
                    course.getNote()));

            // System.out.println(course);
        }

        ObservableList<CourseTM> items = tblCourses.getItems();

        List<String> firstColumnData = new ArrayList<>();
        // System.out.println(items.get(0).getCourseID());
        System.out.println(tblCourses.getItems().size());*/

        /*for (int i = 0; i < tblCourses.getItems().size(); i++) {

            if (firstColumnData.contains(items.get(i).getCourseID())) {
                String newBatchId = items.get(i).getSelectedBatch().getId();
                System.out.println(newBatchId);
                int origin = firstColumnData.indexOf(items.get(i).getCourseID());
                ComboBox batchIdCell = (ComboBox) tblCourses.getColumns().get(2).getCellObservableValue(origin).getValue();
                batchIdOptions.add(String.valueOf(newBatchId));
                System.out.println(batchIdOptions);
                batchIdCell.setItems(batchIdOptions);
            } else {
                firstColumnData.add(items.get(i).getCourseID());
                System.out.println(firstColumnData.get(i));
            }
        }*/

    }

    private void checkAlreadyInTable(String id) {
        // System.out.println("run");

        CourseTM courseTM = tblCourses.getItems().get(0);
        if (id == courseTM.getCourseID()) {
            System.out.println("equals");
        }
    }

    public void btnAddCourse_OnAction(ActionEvent actionEvent) {
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
            secondaryStage.setTitle("Add new Course");

            ctrl.navigate("Add new Course", "/view/AddCourseForm.fxml", AppBarIcon.NAV_ICON_NONE);

            secondaryStage.show();
            tblCourses.refresh();

            txtQuery.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
