/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service;

import model.Batch;
import model.Course;
import service.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BatchService {
    private static final List<Course> courseDB = new ArrayList<>();

    static {

    }

    private final Set<String> coursesList = new HashSet<>();

    public BatchService() {

    }

    public void initialize() {

    }

    public void saveCourse(Course course) {

    }

    private boolean exitsCourse(String courseID) {
        for (Course course : courseDB) {

            if (course.getCourseID().equals(courseID)) {
                return true;
            }
        }
        return false;
    }

    private void findDuplicatedRecord(Course course) {
        System.out.println(course.getCourseID() + " , " + course.getSelectedBatch().getId());

        for (Course course1 : courseDB) {


        }

    }

    public void updateCourse(Course course) throws NotFoundException {
        Course c = findCourse(course.getCourseID());
        courseDB.set(courseDB.indexOf(c), course);
    }

    public void deleteCourse(String courseId) throws NotFoundException {
        Course course = findCourse(courseId);
        courseDB.remove(course);

    }

    public List<Course> findAllCourses() {
        return courseDB;
    }

    public Course findCourse(String courseId) throws NotFoundException {
        for (Course course : courseDB) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        throw new NotFoundException();
    }

    public Boolean isCourseExists(String inputCourseID, String inputBatchId) {
        for (Course course : courseDB) {
            //String[] rawCourseId = course.getCourseID().split("-");
            if (course.getCourseID().equals(inputCourseID.trim()) && course.getSelectedBatch().getId().equals(inputBatchId.trim())) {

                return true;
            }
        }
        return false;
    }

    public List<Course> findCourses(String query) {
        List<Course> result = new ArrayList<>();
        for (Course course : courseDB) {

            if (course.getCourseName().contains(query) ||
                    course.getCourseID().contains(query)) {

                for (Course course1 : result) {

                }
                result.add(course);
            }

        }
        return result;
    }

    public Set<Course> findDuplicates(List<Course> listContainingDuplicates) {
        final Set<Course> duplicateCourseIds = new HashSet<>();
        final Set<Course> notDuplicates = new HashSet<>();
        final Set<Course> set1 = new HashSet<>();

        for (Course courseList : listContainingDuplicates) {
            if (!set1.add(courseList)) {
                duplicateCourseIds.add(courseList);
            } else {
                notDuplicates.add(courseList);
            }
        }
        // System.out.println(notDuplicates);
        return notDuplicates;
    }

    public Set<String> getAllCourses() {
        Set<String> courseSet = new HashSet<String>();

        for (Course course : courseDB) {
            courseSet.add(course.getCourseID());
        }

        //System.out.println(courseSet);

        return courseSet;
    }

    public String getCourseNameUsingId(String id) {
        for (Course course : courseDB) {
            if (course.getCourseID().equals(id)) {
                return course.getCourseName();
            }
        }
        return null;
    }
}
