/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
    private String nic;
    private String fullName;
    private String contactNumber;
    private String address;
    private LocalDate dateOfBirth;
    private String emailAddress;
    private String courseId;
    private String batchID;

    public Student() {
    }

    public Student(String nic, String fullName, String contactNumber, String address, LocalDate dateOfBirth, String emailAddress, String courseId, String batchID) {
        this.nic = nic;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.courseId = courseId;
        this.batchID = batchID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {

        this.courseId = courseId;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nic='" + nic + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailAddress='" + emailAddress + '\'' +
                ", courseId='" + courseId + '\'' +
                ", batchID='" + batchID + '\'' +
                '}';
    }
}
