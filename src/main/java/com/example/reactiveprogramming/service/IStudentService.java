package com.example.reactiveprogramming.service;

import com.example.reactiveprogramming.serviceDto.request.AddStudentRequest;
import com.example.reactiveprogramming.serviceDto.request.UpdateStudentRequest;
import com.example.reactiveprogramming.serviceDto.response.AddStudentResponse;
import com.example.reactiveprogramming.serviceDto.response.GetActiveStudentsResponse;
import com.example.reactiveprogramming.serviceDto.response.GetStudentResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface IStudentService {
    Single<AddStudentResponse> addStudent(AddStudentRequest addStudentRequest);
    Completable updateStudent(UpdateStudentRequest updateStudentRequest);
    Single<GetStudentResponse> getStudent(Long studentId);
    Observable<List<GetStudentResponse>> getActiveStudents();
    Completable deleteStudent(Long studentId);
}
