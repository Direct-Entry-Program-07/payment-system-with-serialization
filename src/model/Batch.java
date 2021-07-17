/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.time.LocalDate;

public class Batch {
    private String id;
    private int studentCount;
    private LocalDate commencingDate;
    private String note;

    public Batch() {
    }

    public Batch(String id, int studentCount, LocalDate commencingDate, String note) {
        this.setId(id);
        this.setStudentCount(studentCount);
        this.setCommencingDate(commencingDate);
        this.setNote(note);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public LocalDate getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(LocalDate commencingDate) {
        this.commencingDate = commencingDate;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return id;
    }
}
