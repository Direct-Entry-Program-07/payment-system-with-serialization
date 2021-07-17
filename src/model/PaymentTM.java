/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.math.BigDecimal;

public class PaymentTM {
    private String receiptNumber;
    private String nic;
    private String studentName;
    private String courseName;
    private BigDecimal totalPayment;
    private BigDecimal balance;

    public PaymentTM() {
    }

    public PaymentTM(String receiptNumber,String nic, String studentName, String courseName, BigDecimal totalPayment, BigDecimal balance) {
        this.setReceiptNumber(receiptNumber);
        this.nic = nic;
        this.studentName = studentName;
        this.courseName = courseName;
        this.totalPayment = totalPayment;
        this.balance = balance;
    }

    public PaymentTM(String receiptNumber,String nic, BigDecimal totalFee, BigDecimal balance) {
        this.setReceiptNumber(receiptNumber);
        this.nic = nic;
        this.totalPayment = totalFee;
        this.balance = balance;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getTotalFee() {
        return totalPayment;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalPayment = totalFee;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "receiptNumber='" + receiptNumber + '\'' +
                ", nic='" + nic + '\'' +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", totalFee=" + totalPayment +
                ", balance=" + balance +
                '}';
    }
}
