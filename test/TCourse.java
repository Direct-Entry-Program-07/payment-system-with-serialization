/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import model.Batch;

import java.io.Serializable;

public class TCourse implements Serializable {

    private String courseId;
    private String courseName;
    private TBatch courseBatches;

    public TCourse(String courseId) {
        this.courseId = courseId;
    }

    public TCourse(String courseId, String courseName, TBatch courseBatches) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseBatches = courseBatches;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public TBatch getCourseBatches() {
        return courseBatches;
    }

    public void setCourseBatches(TBatch courseBatches) {
        this.courseBatches = courseBatches;
    }

    @Override
    public String toString() {
        return "TCourse{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseBatches=" + courseBatches +
                '}';
    }
}
