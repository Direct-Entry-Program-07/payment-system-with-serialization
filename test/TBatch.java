/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TBatch implements Serializable {
    private String referenceCourseId;
    private List<Integer> batches = new ArrayList<>();
    private int batch;
    private int studentCount;
    private List<Integer> students = new ArrayList<>();

    public TBatch() {
    }

    public TBatch(String referenceCourseId, int batch, int studentCount) {
        this.referenceCourseId = referenceCourseId;
        this.setBatch(batch);
        this.setStudentCount(studentCount);
    }

    public String getReferenceCourseId() {
        return referenceCourseId;
    }

    public void setReferenceCourseId(String referenceCourseId) {
        this.referenceCourseId = referenceCourseId;
    }

    public List<Integer> getBatches() {
        return batches;
    }

    public void setBatches(List<Integer> batches) {
        this.batches = batches;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
