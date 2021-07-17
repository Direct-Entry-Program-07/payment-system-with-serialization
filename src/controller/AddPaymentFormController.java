/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Payment;
import model.PaymentTM;
import service.PaymentService;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AddPaymentFormController {
    public JFXButton btnSave;
    public JFXButton btnCancel;
    public TextField txtStudentNIC;
    public ComboBox cmbCourseName;
    public TextField txtTotalFee;
    public TextField txtRemaining;
    public ComboBox cmbPaymentReason;
    public TextField txtPaymentAmount;
    public ComboBox cmbPaymentMethod;
    public TextField txtRefNumber;
    public TextField txtBalance;
    public TextArea txtNote;
    public AnchorPane root;

    public PaymentService paymentService = new PaymentService();
    ObservableList<String> courseNames = FXCollections.observableArrayList();
    ObservableList<String> paymentReasons = FXCollections.observableArrayList();
    ObservableList<String> paymentMethod = FXCollections.observableArrayList();



    public void initialize() {

        courseNames.add("DEP");
        courseNames.add("ABSD");
        courseNames.add("GDSE");

        Platform.runLater(() -> {

            if (root.getUserData() != null) {
                PaymentTM tm = (PaymentTM) root.getUserData();
                Payment payment = paymentService.findPayment(tm.getReceiptNumber());

                txtStudentNIC.setText(payment.getNic());
                cmbCourseName.setItems(courseNames);
                txtTotalFee.setText(String.valueOf(payment.getTotalFee()));
                txtRemaining.setText(String.valueOf(payment.getRemaining()));
                cmbPaymentReason.setItems(paymentReasons);
                txtPaymentAmount.setText(String.valueOf(payment.getPaymentAmount()));
                cmbPaymentMethod.setItems(paymentMethod);
                txtRefNumber.setText(txtRefNumber.getText());
                txtBalance.setText(String.valueOf(txtBalance.getText()));
                txtNote.setText(txtNote.getText());

                btnSave.setText("Update Payment");
            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        try {
            if (!isValidated()) {
                return;
            }
            Payment payment = new Payment(
                    txtRefNumber.getText(),
                    txtStudentNIC.getText(),
                    "Student Name",
                    "DEP",
                    new BigDecimal(txtTotalFee.getText()),
                    new BigDecimal(txtRemaining.getText()),
                    (String) cmbPaymentReason.getValue(),
                    new BigDecimal(txtPaymentAmount.getText()),
                    (String) cmbPaymentMethod.getValue(),
                    new BigDecimal(txtBalance.getText()),
                    txtNote.getText()
            );

            if (btnSave.getText().equals("Save Payment")) {
                paymentService.savePayment(payment);
            } else {
                PaymentTM tm = (PaymentTM) root.getUserData();
                tm.setNic(txtStudentNIC.getText());
                tm.setStudentName("Student Name");
                tm.setTotalFee(new BigDecimal(txtTotalFee.getText()));
                tm.setBalance(new BigDecimal(txtBalance.getText()));
                paymentService.updatePayment(payment);
            }
            new Alert(Alert.AlertType.NONE, "Payment has been saved successfully", ButtonType.OK).show();

        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the payment", ButtonType.OK).show();
        }
    }

    private boolean isValidated() {
        String nic = txtStudentNIC.getText();
        System.out.println((String) cmbCourseName.getValue());
        String courseName = (String) cmbCourseName.getValue();
        String totalFee = txtTotalFee.getText();
        String remaining = txtRemaining.getText();
        BigDecimal bigOfRemain = new BigDecimal(txtRemaining.getText());
        String reasonToPay = cmbPaymentReason.getValue().toString();
        String payment = txtPaymentAmount.getText();
        BigDecimal bigOfPayment = new BigDecimal(txtPaymentAmount.getText());
        String paymentMethod = cmbPaymentMethod.getValue().toString();
        String refNumber = txtRefNumber.getText();
        String balance = txtBalance.getText();
        BigDecimal bigBalance = new BigDecimal(txtBalance.getText());

        Pattern nicPattern1 = Pattern.compile("(\\d{9}V)|(\\d{9}v)");
        Pattern nicPattern2 = Pattern.compile("\\d{12}");
        Pattern bigDecimalPattern = Pattern.compile("[0-9]+[,]*");
        Pattern refNumberPattern = Pattern.compile("\\d");


        if (!(nicPattern1.matcher(nic).matches() || nicPattern2.matcher(nic).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC").show();
            txtStudentNIC.requestFocus();
            return false;
        } else if (courseName.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Please select a course Name").show();
            cmbCourseName.requestFocus();
            return false;
        } else if (!(bigDecimalPattern.matcher(totalFee).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid Course fee").show();
            txtTotalFee.requestFocus();
            return false;
        } else if (reasonToPay.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Please select a Reason to pay").show();
            cmbPaymentReason.requestFocus();
            return false;
        } else if (!(bigDecimalPattern.matcher(totalFee).matches())) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount").show();
            txtPaymentAmount.requestFocus();
            return false;
        } else if (bigOfPayment.compareTo(bigOfRemain) > 0) {
            new Alert(Alert.AlertType.ERROR, "Check the amount. Payment amount can't be higher than the remaining amount").show();
            txtPaymentAmount.requestFocus();
            return false;
        } else if (paymentMethod.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment method").show();
            cmbPaymentReason.requestFocus();
            return false;
        }else if ((paymentMethod.equals("Bank Deposit")) && refNumber.equals("")){
            new Alert(Alert.AlertType.ERROR, "Please Add the Reference Number of the Slip").show();
            cmbPaymentReason.requestFocus();
            return false;
        }

        return false;
    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
        Window window = btnCancel.getScene().getWindow();
        ((Stage) window).close();
    }
}
