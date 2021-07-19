/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

import model.Course;

import java.util.*;

public class TCourseService {

    private static final List<TCourse> db = new ArrayList<>();
    public static Set<String> pack = new HashSet<>();
    public static Set<TBatch> b = new HashSet<>();

    static {
        TCourse tc1 = new TCourse("DEP", "Direct Entry Program", new TBatch("DEP", 1, 10));
        TCourse tc2 = new TCourse("DEP", "Direct Entry Program", new TBatch("DEP", 2, 20));
        TCourse tc3 = new TCourse("DEP", "Direct Entry Program", new TBatch("DEP", 3, 15));
        TCourse tc4 = new TCourse("DEP", "Direct Entry Program", new TBatch("DEP", 3, 15));
        TCourse tc5 = new TCourse("RWAD", "Temp c2", new TBatch("RWAD", 1, 50));

        db.add(tc1);
        db.add(tc2);
        db.add(tc3);
        db.add(tc4);
        db.add(tc5);
    }

    public static void main(String[] args) {
        for (TCourse record : db) {
            pack.add(record.getCourseId());
        }

        System.out.println(pack);


    }
}
