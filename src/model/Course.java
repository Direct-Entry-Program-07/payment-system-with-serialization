/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.time.LocalDate;

public class Course {
    private String courseID;
    private String courseName;
    private Batch selectedBatch;
    private int noOfStudentsForTheBatch;
    private LocalDate batchCommencingDate;
    private String note;
    private boolean isDuplicated = false;

    public Course() {
    }

    public Course(String courseID, String courseName, Batch selectedBatch) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.selectedBatch = selectedBatch;
    }

    public Course(String courseID, String courseName, Batch selectedBatch, int noOfStudentsForTheBatch, LocalDate batchCommencingDate, String note) {

        this.setCourseID(courseID);
        this.setCourseName(courseName);
        this.setSelectedBatch(selectedBatch);
        this.setNoOfStudentsForTheBatch(noOfStudentsForTheBatch);
        this.setBatchCommencingDate(batchCommencingDate);
        this.setNote(note);
    }


    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Batch getSelectedBatch() {
        return selectedBatch;
    }

    public void setSelectedBatch(Batch selectedBatch) {
        this.selectedBatch = selectedBatch;
    }

    public int getNoOfStudentsForTheBatch() {
        return noOfStudentsForTheBatch;
    }

    public void setNoOfStudentsForTheBatch(int noOfStudentsForTheBatch) {
        this.noOfStudentsForTheBatch = noOfStudentsForTheBatch;
    }

    public LocalDate getBatchCommencingDate() {
        return batchCommencingDate;
    }

    public void setBatchCommencingDate(LocalDate batchCommencingDate) {
        this.batchCommencingDate = batchCommencingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", selectedBatch=" + selectedBatch +
                ", noOfStudentsForTheBatch=" + noOfStudentsForTheBatch +
                ", batchCommencingDate=" + batchCommencingDate +
                ", note='" + note + '\'' +
                '}';
    }

    public boolean isDuplicated() {
        return true;
    }

    public void setDuplicated(boolean duplicated) {
        isDuplicated = duplicated;
    }
}
