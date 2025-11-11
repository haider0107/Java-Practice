package com.example.SpringJDBCEx.repository;

import com.example.SpringJDBCEx.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    public void save(Student s) {
        System.out.println(s.toString());
    }

    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();
        return students;
    }
}
