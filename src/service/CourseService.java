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

public class CourseService {
    private static final List<Course> courseDB = new ArrayList<>();

    static {
/*
        // add dummy data to course table
        Course c1 = new Course("DEP", "Direct Entry Program", new Batch("1", 20, LocalDate.of(2021, 3, 3), "Note1"));
        Course c2 = new Course("GDSE", "Graduate Diploma in Software Engineering", new Batch("1", 20, LocalDate.of(2021, 5, 3), "Note 2"));
        Course c3 = new Course("CMJD", "CMJD Professional", new Batch("1", 30, LocalDate.of(2021, 3, 3), "Note 3"));
        Course c4 = new Course("ABSD", "Advanced Business Solution Developer", new Batch("1", 40, LocalDate.of(2021, 3, 3), "Note 4"));
        Course c5 = new Course("RWAD", "Rapid Web App Developer", new Batch("1", 25, LocalDate.of(2021, 3, 3), "Note 5"));
        // Course c6 = new Course("DEP", "Direct Entry Program", new Batch("2", 35, LocalDate.of(2021, 10, 3), "Note 6"));
        courseDB.add(c1);
        courseDB.add(c2);
        courseDB.add(c3);
        courseDB.add(c4);
        courseDB.add(c5);
        // courseDB.add(c6);
*/

        Course c1 = new Course("DEP", "Direct Entry Program", new Batch("1", 20, LocalDate.of(2021, 3, 3), "Note1"));
        Course c2 = new Course("DEP", "Direct Entry Program", new Batch("2", 20, LocalDate.of(2022, 3, 3), "Note2"));
        courseDB.add(c1);
        courseDB.add(c2);
    }

    private final Set<String> coursesList = new HashSet<>();

    public CourseService() {

    }

    public void initialize() {
        System.out.println("working");
        for (Course course : courseDB) {
            if (course.getCourseID().equals("DEP")){
                course.getBatchDetails(course.getCourseID(), 1);
            }
        }
    }

    public void saveCourse(Course course) {
        if (exitsCourse(course.getCourseID())) {
            //System.out.println("duplicated");
            for (Course c : courseDB) {
                if (c.getCourseID().equals(course.getCourseID())) {
                    String id = c.getSelectedBatch().getId();
                    c.setSelectedBatch(new Batch(course.getSelectedBatch().getId(), course.getNoOfStudentsForTheBatch(), course.getBatchCommencingDate(), course.getNote()));
                    List<Integer> allBatches = c.getAllBatches();

                    allBatches.add(Integer.parseInt(c.getSelectedBatch().getId()));
                    c.setAllBatches(allBatches);
                }
            }

        }

        System.out.println();

       /* boolean isDuplicatedFound = false;

        for (Course c : courseDB) {
            coursesList.add(c.getCourseID());
        }

       // System.out.println(coursesList);


        if (coursesList.isEmpty()) {
            courseDB.add(course);

            coursesList.add(course.getCourseID());
            return;
        } else {
            for (String s : coursesList) {

                if (course.getCourseID().equals(s)) {
                   isDuplicatedFound = true;
                   courseDB.add(course);
                   findDuplicatedRecord(course);
                }
            }

            if (!isDuplicatedFound){            //prevent saving the new batch as a new course
                courseDB.add(course);
                coursesList.add(course.getCourseID());
            }
        }*/

        // System.out.println(courseDB);
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
/*
        Set<String> courseSet = new HashSet<String>();

        for (Course course : courseDB) {
            courseSet.add(course.getCourseID());
        }
*/

        List<Course> result = new ArrayList<>();
        for (Course course : courseDB) {

            if (course.getCourseName().contains(query) ||
                    course.getCourseID().contains(query)) {

                for (Course course1 : result) {
                        //  course1.get
                }
                result.add(course);
            }

        }
        //System.out.println(result);
       /* Set<Course> filteredSet = findDuplicates(result);
        List<Course> filteredList = new ArrayList<>(filteredSet);
        filteredList.addAll(filteredSet);*/
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
