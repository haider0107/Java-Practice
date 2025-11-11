package com.org.spring_data_jpa_ex;

import com.org.spring_data_jpa_ex.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {

//    @Query("select s from Student s where s.name = ?1")
    List<Student> findByName(String name);

    List<Student> findByMarks(int marks);

    List<Student> findByMarksGreaterThan(int marks);
}
