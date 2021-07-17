/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service;

import model.Student;
import service.exception.DuplicateEntryException;
import service.exception.FailedOperationException;

import javax.security.auth.login.FailedLoginException;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private static final File studentFile = new File("students.dep7");
    private static final List<Student> studentDB = new ArrayList<>();

    static {
        //Add dummy data to student table
        Student s1 = new Student("123456789V", "Dinesh Karunarathna", "071-2345678", "No: 23, Galle", LocalDate.of(1993, 8, 8), "dinesh@ijse.lk", "DEP-6", "6");
        Student s2 = new Student("763546374657", "Sampath kumara", "092-3647562", "A/S, Kandy", LocalDate.of(1998, 3, 4), "sampath@gmail.com", "CMJD-15", "15");
        Student s3 = new Student("234567846738", "Adeesha Perera", "012-2647583", "3/2, abcd, matara", LocalDate.of(1996, 5, 6), "perera@yahool.com", "GDSE-13", "13");
        Student s4 = new Student("234563746v", "Isuru Udana", "045-2348763", "2, Colombo", LocalDate.of(1997, 3, 4), "issa@ijse.com", "ABSD-4", "4");
        studentDB.add(s1);
        studentDB.add(s2);
        studentDB.add(s3);
        studentDB.add(s4);
    }

    public StudentService() {

    }

    public void saveStudent(Student student) throws DuplicateEntryException, FailedOperationException {
        if (exitsStudent(student.getNic())){
            throw new DuplicateEntryException();
        }

        try {
            studentDB.add(student);
            writeDataToFile();
        }catch (FailedOperationException e){
            studentDB.remove(student);
            throw e;
        }
    }

    private void writeDataToFile() throws FailedOperationException {
        try(FileOutputStream fos = new FileOutputStream(studentFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(studentDB);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new FailedOperationException();
        }
    }

    public void updateStudent(Student student){
        Student s = findStudent(student.getNic());
        int index = studentDB.indexOf(s);
        studentDB.set(index, student);
    }

    public void deleteStudent(String nic){
        Student student = findStudent(nic);
        studentDB.remove(student);

    }

    public List<Student> findAllStudents(){
        return studentDB;
    }

    public Student findStudent(String nic){
        for (Student student : studentDB) {
            if (student.getNic().equals(nic)){
                return student;
            }
        }
        return null;
    }

    public List<Student> findStudents(String query){
        List<Student> result = new ArrayList<>();

        for (Student student: studentDB) {

            if (student.getFullName().contains(query) ||
                    student.getNic().contains(query) ||
                    student.getAddress().contains(query) ||
                    student.getContactNumber().contains(query) ||
                    student.getEmailAddress().contains(query) ||
                    student.getCourseId().contains(query)){
                result.add(student);
            }
        }
        return result;
    }

    private boolean exitsStudent(String nic){
        for (Student student : studentDB) {

            if (student.getNic().equals(nic)) {
                return true;
            }
        }
        return false;
    }
}
