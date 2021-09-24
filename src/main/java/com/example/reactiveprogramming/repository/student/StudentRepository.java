package com.example.reactiveprogramming.repository.student;

import com.example.reactiveprogramming.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByActiveTrue();
}