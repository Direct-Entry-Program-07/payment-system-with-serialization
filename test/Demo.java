/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import model.Batch;
import model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        CourseServiceTest courseServiceTest = new CourseServiceTest();
        Management mgmt = new Management();
        List<String> allList = new ArrayList<>();
/*
        allList.add("DEP");
        allList.add("ABSD");
        allList.add("CMJD");
        allList.add("AWSD");
        allList.add("DEP");
        allList.add("AWSD");*/

        mgmt.duplicateFinder();

    }
}
