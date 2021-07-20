/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import service.StudentService;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AddPaymentFormController {
    private int receiptNumber = 0;
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
    private String formattedRec;

    public void initialize() {

        formattedRec = String.format("R-" + "%04d", receiptNumber);

        courseNames.add("DEP");
        courseNames.add("ABSD");
        courseNames.add("GDSE");

        paymentReasons.add("Registration Fee");
        paymentReasons.add("Installments");
        paymentReasons.add("Additional fee");
        paymentReasons.add("Exam Fee");

        paymentMethod.add("Cash");
        paymentMethod.add("Bank Payment");

        cmbCourseName.setItems(courseNames);
        cmbPaymentReason.setItems(paymentReasons);
        cmbPaymentMethod.setItems(paymentMethod);

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

        txtStudentNIC.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    String text = txtStudentNIC.getText();
                    boolean b = new StudentService().exitsStudent(text);

                    if (b) {
                        txtStudentNIC.setStyle("-fx-text-fill: Green; -fx-border-color: Green;");

                    } else {
                        txtStudentNIC.setStyle("-fx-text-fill: Red; -fx-border-color: Red; ");
                    }
                }
            }
        });
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        try {
            if (!isValidated()) {
                return;
            }
            String fullName = new StudentService().findStudent(txtStudentNIC.getText()).getFullName();
            receiptNumber++;
            formattedRec = String.format("R-" + "%04d", receiptNumber);

            Payment payment = new Payment(
                    formattedRec,
                    txtStudentNIC.getText(),
                    fullName,
                    (String) cmbCourseName.getValue(),
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
        } catch (FailedOperationException e) {
            e.printStackTrace();
        } catch (DuplicateEntryException e) {
            e.printStackTrace();
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
        }else if(!new StudentService().exitsStudent(txtStudentNIC.getText())){
            new Alert(Alert.AlertType.ERROR, "Can't find a student matches to the given NIC").show();
            txtStudentNIC.requestFocus();
            return false;
        }else if (courseName.equals("")) {
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
        } else if ((paymentMethod.equals("Bank Deposit")) && refNumber.equals("")) {
            new Alert(Alert.AlertType.ERROR, "Please Add the Reference Number of the Slip").show();
            cmbPaymentReason.requestFocus();
            return false;
        }

        return true;
    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
        Window window = btnCancel.getScene().getWindow();
        ((Stage) window).close();
    }
}
