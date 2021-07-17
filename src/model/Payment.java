/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import javafx.collections.ObservableList;

import java.math.BigDecimal;

public class Payment {
    private String receiptNumber;
    private String nic;
    private String courseName;
    private BigDecimal totalFee;
    private BigDecimal remaining;
    private String paymentReason;
    private BigDecimal paymentAmount;
    private String paymentMethod;
    private BigDecimal balance;
    private String note;
    private String studentName;

    public Payment() {
    }

    public Payment(String receiptNumber, String nic, String studentName, String courseName, BigDecimal totalFee, BigDecimal remaining, String paymentReason, BigDecimal paymentAmount, String paymentMethod, BigDecimal balance, String note) {
        this.receiptNumber = receiptNumber;
        this.nic = nic;
        this.studentName = studentName;
        this.courseName = courseName;
        this.totalFee = totalFee;
        this.remaining = remaining;
        this.paymentReason = paymentReason;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
        this.balance = balance;
        this.note = note;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }

    public String getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(String paymentReason) {
        this.paymentReason = paymentReason;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "receiptNumber=" + receiptNumber +
                ", nic='" + nic + '\'' +
                ", courseName=" + courseName +
                ", totalFee=" + totalFee +
                ", remaining=" + remaining +
                ", paymentReason=" + paymentReason +
                ", paymentAmount=" + paymentAmount +
                ", paymentMethod=" + paymentMethod +
                ", balance=" + balance +
                ", note='" + note + '\'' +
                '}';
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
