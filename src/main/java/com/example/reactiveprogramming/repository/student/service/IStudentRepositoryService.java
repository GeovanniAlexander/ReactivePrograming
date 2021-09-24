package com.example.reactiveprogramming.repository.student.service;

import com.example.reactiveprogramming.entity.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepositoryService {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findByActiveTrue();
    void delete(Student student);
}
