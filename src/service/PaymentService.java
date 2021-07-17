/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private static final List<Payment> paymentDB = new ArrayList<>();
    private static ObservableList<String> courseList = FXCollections.observableArrayList();


    static {
        courseList.add("DEP");
       // courseList.add("GDSE");
       // courseList.add("ABSD");
       // courseList.add("ABSD");

        //Add dummy data to payment table
        Payment p1 = new Payment("R-0001","123456789v", "Amal Perera", "DEP", BigDecimal.valueOf(100000), BigDecimal.valueOf(30000), "1st Installement", BigDecimal.valueOf(20000), "Cash", BigDecimal.valueOf(10000), "Test");
        Payment p2 = new Payment("R-0002","234567890v", "Kasun Sampath", "CMJD", BigDecimal.valueOf(200000), BigDecimal.valueOf(50000), "Registration", BigDecimal.valueOf(20000), "Back Deposit", BigDecimal.valueOf(30000), "Test");
        paymentDB.add(p1);
        paymentDB.add(p2);
    }

    public PaymentService(){

    }

    public void savePayment(Payment payment){
        paymentDB.add(payment);
    }

    public void updatePayment(Payment payment){
        Payment p = findPayment(payment.getReceiptNumber());
        int index = paymentDB.indexOf(p);
        paymentDB.set(index, payment);
    }

    public void deletePayment(String receiptNo){
        Payment payment = findPayment(receiptNo);
        paymentDB.remove(payment);
    }

    public List<Payment> findAllPayments(){
        return paymentDB;
    }

    public Payment findPayment(String receiptNo){
        for (Payment payment : paymentDB) {
            if (payment.getReceiptNumber().equals(receiptNo)){
                return payment;

            }
        }
        return null;
    }

    public List<Payment> findPayments(String query){
        List<Payment> result = new ArrayList<>();

        for (Payment payment : paymentDB) {

            if (payment.getReceiptNumber().contains(query) ||
                payment.getCourseName().contains(query) ||
                payment.getNic().contains(query)){
                result.add(payment);
            }
        }
        return result;
    }
}
