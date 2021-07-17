/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import model.Batch;
import model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Management {
    private static final List<Course> courseDB = new ArrayList<>();
    CourseServiceTest courseServiceTest = new CourseServiceTest();

    static {


        // add dummy data to course table
        Course c1 = new Course("DEP", "Direct Entry Program", new Batch("1", 20, LocalDate.of(2021, 3, 3), "Note1"));
        Course c2 = new Course("GDSE", "Graduate Diploma in Software Engineering", new Batch("1", 20, LocalDate.of(2021, 5, 3), "Note 2"));
        Course c3 = new Course("CMJD", "CMJD Professional", new Batch("1", 30, LocalDate.of(2021, 3, 3), "Note 3"));
        Course c4 = new Course("ABSD", "Advanced Business Solution Developer", new Batch("1", 40, LocalDate.of(2021, 3, 3), "Note 4"));
        Course c5 = new Course("RWAD", "Rapid Web App Developer", new Batch("1", 25, LocalDate.of(2021, 3, 3), "Note 5"));
        Course c6 = new Course("DEP", "Direct Entry Program", new Batch("2", 35, LocalDate.of(2021, 10, 3), "Note 6"));
        courseDB.add(c1);
        courseDB.add(c2);
        courseDB.add(c3);
        courseDB.add(c4);
        courseDB.add(c5);
        courseDB.add(c6);

    }

    public void duplicateFinder(){
        Set<Course> duplicates = courseServiceTest.findDuplicates(courseDB);
        System.out.println(duplicates);
    }

}
