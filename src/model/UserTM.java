/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.time.LocalDate;

public class UserTM {
    private String fullname;
    private String userType;
    private String username;
    private String address;
    private String contactNumber;
    private String email;
    private LocalDate joinedDate;

    public UserTM() {
    }

    public UserTM(String fullname, String userType, String username, String address, String contactNumber, String email, LocalDate joinedDate) {
        this.fullname = fullname;
        this.userType = userType;
        this.username = username;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.joinedDate = joinedDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    @Override
    public String toString() {
        return "USerTM{" +
                "username='" + fullname + '\'' +
                ", userType='" + userType + '\'' +
                ", nic='" + username + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", joinedDate=" + joinedDate +
                '}';
    }
}
