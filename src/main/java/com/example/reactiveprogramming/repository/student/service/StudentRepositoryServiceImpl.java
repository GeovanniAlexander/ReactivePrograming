package com.example.reactiveprogramming.repository.student.service;

import com.example.reactiveprogramming.entity.Student;
import com.example.reactiveprogramming.repository.student.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentRepositoryServiceImpl implements IStudentRepositoryService {

    private final StudentRepository studentRepository;

    public Student save(Student student){
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findByActiveTrue() {
        return studentRepository.findByActiveTrue();
    }

    @Override
    public void delete(Student student) {
        studentRepository.delete(student);
    }

}
