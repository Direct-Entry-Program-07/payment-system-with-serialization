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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Payment;
import model.PaymentTM;
import model.StudentTM;
import service.PaymentService;
import util.AppBarIcon;
import util.MaterialUI;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class ManagePaymentsFormController {
    private final PaymentService paymentService = new PaymentService();
    public TextField txtQuery;
    public TableView<PaymentTM> tblPayments;
    public JFXButton btnAddPayment;

    public void initialize() {

        MaterialUI.paintTextFields(txtQuery);

        tblPayments.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        tblPayments.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPayments.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tblPayments.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("courseName"));
        tblPayments.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("totalFee"));
        tblPayments.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<PaymentTM, HBox> operatorCol = (TableColumn<PaymentTM, HBox>) tblPayments.getColumns().get(6);

        operatorCol.setCellValueFactory(param -> {
            ImageView imgEdit = new ImageView("/view/assets/icons/Edit.png");
            ImageView imgTrash = new ImageView("/view/assets/icons/Trash.png");

            imgEdit.getStyleClass().add("action-icons");
            imgTrash.getStyleClass().add("action-icons");

            imgEdit.setOnMouseClicked(event -> updatePayment(param.getValue()));
            imgTrash.setOnMouseClicked(event -> deletePayment(param.getValue()));


            return new ReadOnlyObjectWrapper<>(new HBox(10, imgEdit, imgTrash));
        });

        txtQuery.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                loadAllPayments(newValue);
            }
        });

        tblPayments.itemsProperty().addListener(new ChangeListener<ObservableList<PaymentTM>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<PaymentTM>> observable, ObservableList<PaymentTM> oldValue, ObservableList<PaymentTM> newValue) {
                if (newValue != null){
                    tblPayments.refresh();
                }
            }
        });

        loadAllPayments("");
    }

    private void deletePayment(PaymentTM tm) {
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this payment? ", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                paymentService.deletePayment(tm.getReceiptNumber());
                tblPayments.getItems().remove(tm);
            }

        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item", ButtonType.OK).show();
        }
    }

    private void updatePayment(PaymentTM tm) {
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
            secondaryStage.setTitle("Update Payment");
            secondaryStage.centerOnScreen();
            ctrl.navigate("Update Payment", "/view/AddPaymentForm.fxml", AppBarIcon.NAV_ICON_NONE, null, tm);

            secondaryStage.showAndWait();
            tblPayments.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllPayments(String query) {
        tblPayments.getItems().clear();

        for (Payment payment : paymentService.findPayments(query)) {

            BigDecimal totalPayment = payment.getTotalFee().subtract(payment.getBalance());

            tblPayments.getItems().add(new PaymentTM(
                    payment.getReceiptNumber(),
                    payment.getNic(),
                    payment.getStudentName(),
                    String.valueOf(payment.getCourseName()),
                    totalPayment,
                    payment.getBalance()
            ));
        }
    }

    public void btnAddPayment_OnAction(ActionEvent actionEvent) {
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
            secondaryStage.setTitle("Add new Payment");

            ctrl.navigate("Add new Payment", "/view/AddPaymentForm.fxml", AppBarIcon.NAV_ICON_NONE);

            secondaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
