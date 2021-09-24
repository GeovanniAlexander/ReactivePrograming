package com.example.reactiveprogramming.service;

import com.example.reactiveprogramming.entity.Student;
import com.example.reactiveprogramming.repository.student.service.IStudentRepositoryService;
import com.example.reactiveprogramming.serviceDto.request.AddStudentRequest;
import com.example.reactiveprogramming.serviceDto.request.UpdateStudentRequest;
import com.example.reactiveprogramming.serviceDto.response.AddStudentResponse;
import com.example.reactiveprogramming.serviceDto.response.GetStudentResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService{

    private final IStudentRepositoryService studentRepository;

    @Override
    public Single<AddStudentResponse> addStudent(AddStudentRequest addStudentRequest) {
        return saveStudentToRepository(addStudentRequest);
    }

    private Single<AddStudentResponse> saveStudentToRepository(AddStudentRequest addStudentRequest) {
        return Single.create(subscriber -> {
            Student student = studentRepository.save(toStudent(addStudentRequest));
            subscriber.onSuccess(new AddStudentResponse(student.getId(), student.getName(), student.getAge()));
        });
    }

    private Student toStudent(AddStudentRequest addStudentRequest) {
        Student student = new Student();
        BeanUtils.copyProperties(addStudentRequest, student);
        return student;
    }

    @Override
    public Completable updateStudent(UpdateStudentRequest updateStudentRequest) {
        return updateStudentToRepository(updateStudentRequest);
    }

    public Completable updateStudentToRepository(UpdateStudentRequest updateStudentRequest) {
        return Completable.create(subscriber -> {
            Optional<Student> optionalStudent = studentRepository.findById(updateStudentRequest.getId());
            if(optionalStudent.isPresent()){
                Student student = new Student();
                BeanUtils.copyProperties(updateStudentRequest, student);
                studentRepository.save(student);
                subscriber.onComplete();
            }else{
                subscriber.onError(new EntityNotFoundException());
            }
        });
    }

    @Override
    public Single<GetStudentResponse> getStudent(Long studentId) {
        return getStudentFromRepository(studentId);
    }

    private Single<GetStudentResponse> getStudentFromRepository(Long studentId) {
        return Single.create(subscriber -> {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if(optionalStudent.isPresent()){
                GetStudentResponse getStudentResponse = new GetStudentResponse();
                BeanUtils.copyProperties(optionalStudent.get(), getStudentResponse);
                subscriber.onSuccess(getStudentResponse);
            }else{
                subscriber.onError(new EntityNotFoundException());
            }
        });
    }

    @Override
    public Observable<List<GetStudentResponse>> getActiveStudents() {
        return getActiveStudentsFromRepository();
    }

    private Observable<List<GetStudentResponse>> getActiveStudentsFromRepository() {
        return Observable.create(subscriber -> {
            List<Student> activeStudents = studentRepository.findByActiveTrue();
            subscriber.onNext(toGetActiveStudentsResponse(activeStudents));
            subscriber.onComplete();
        });
    }

    private List<GetStudentResponse> toGetActiveStudentsResponse(List<Student> activeStudents) {
        return activeStudents.stream().map(i -> {
            GetStudentResponse getActiveStudentsResponse = new GetStudentResponse();
            BeanUtils.copyProperties(i, getActiveStudentsResponse);
            return getActiveStudentsResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public Completable deleteStudent(Long id) {
        return deleteStudentFromRepository(id);
    }

    private Completable deleteStudentFromRepository(Long studentId) {
        return Completable.create(subscriber -> {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()){
                studentRepository.delete(optionalStudent.get());
                subscriber.onComplete();
            } else{
                subscriber.onError(new EntityNotFoundException());
            }
        });
    }


}
