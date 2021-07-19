/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course implements Serializable {
    private String courseID;
    private String courseName;
    private Batch selectedBatch;
    private int noOfStudentsForTheBatch;
    private HashMap<Batch, Batch> batches ;
    private LocalDate batchCommencingDate;
    private String note;
    private List<Integer> allBatches = new ArrayList<>();
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

    public Course(String courseID, String courseName,HashMap<Batch, Batch> batches) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.setBatches(batches);
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

    public boolean isDuplicated() {
        return true;
    }

    public void setDuplicated(boolean duplicated) {
        isDuplicated = duplicated;
    }

    public List<Integer> getAllBatches() {
        return allBatches;
    }

    public void setAllBatches(List<Integer> allBatches) {
        this.allBatches = allBatches;
    }

    public void getBatchDetails(String courseId, int batchNumber){

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
                ", allBatches=" + allBatches +
                ", isDuplicated=" + isDuplicated +
                '}';
    }

    public HashMap<Batch, Batch> getBatches() {
        return batches;
    }

    public void setBatches(HashMap<Batch, Batch> batches) {
        this.batches = batches;
    }
}
