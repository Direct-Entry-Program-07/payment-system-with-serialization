/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Payment;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private static final File paymentsFile = new File("payments.dep7");
    private static final ObservableList<String> courseList = FXCollections.observableArrayList();
    private static List<Payment> paymentDB = new ArrayList<>();

    static {

        readDataFromFile();
        getLastRecieptNumber();
    }

    public PaymentService() {

    }

    private static void writeDataToFile() throws FailedOperationException {
        try (FileOutputStream fos = new FileOutputStream(paymentsFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(paymentDB);

        }catch (Throwable e){
            e.printStackTrace();
            throw new FailedOperationException();
        }
    }

    private static void readDataFromFile() {
        if (!paymentsFile.exists()) return;

        try (FileInputStream fis = new FileInputStream(paymentsFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            paymentDB = (List<Payment>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof EOFException) {
                paymentsFile.delete();
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void getLastRecieptNumber(){
        String last = "";
        for (Payment payment : paymentDB) {
            last = payment.getReceiptNumber();
        }
        System.out.println(last);
    }

    public void savePayment(Payment payment) throws DuplicateEntryException, FailedOperationException {
        if (existsPayment(payment.getReceiptNumber())){
            throw new DuplicateEntryException();
        }
        try {
            paymentDB.add(payment);
            writeDataToFile();
        } catch (FailedOperationException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void updatePayment(Payment payment) {
        Payment p = findPayment(payment.getReceiptNumber());
        int index = paymentDB.indexOf(p);
        paymentDB.set(index, payment);
    }

    public void deletePayment(String receiptNo) {
        Payment payment = findPayment(receiptNo);
        paymentDB.remove(payment);
    }

    public List<Payment> findAllPayments() {
        return paymentDB;
    }

    public Payment findPayment(String receiptNo) {
        for (Payment payment : paymentDB) {
            if (payment.getReceiptNumber().equals(receiptNo)) {
                return payment;

            }
        }
        return null;
    }

    public List<Payment> findPayments(String query) {
        List<Payment> result = new ArrayList<>();

        for (Payment payment : paymentDB) {

            if (payment.getReceiptNumber().contains(query) ||
                    payment.getCourseName().contains(query) ||
                    payment.getNic().contains(query)) {
                result.add(payment);
            }
        }
        return result;
    }

    public boolean existsPayment(String reciept){
        for (Payment payment : paymentDB) {
            if (payment.getReceiptNumber().equals(reciept)){
                return true;
            }
        }
        return false;
    }
}
