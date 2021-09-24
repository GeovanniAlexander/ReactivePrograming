package com.example.reactiveprogramming.controller;

import com.example.reactiveprogramming.controllerDto.request.UpdateStudentWebRequest;
import com.example.reactiveprogramming.controllerDto.response.GetStudentWebResponse;
import com.example.reactiveprogramming.service.IStudentService;
import com.example.reactiveprogramming.serviceDto.request.AddStudentRequest;
import com.example.reactiveprogramming.controllerDto.request.AddStudentWebRequest;
import com.example.reactiveprogramming.controllerDto.response.AddStudentWebResponse;
import com.example.reactiveprogramming.serviceDto.request.UpdateStudentRequest;
import com.example.reactiveprogramming.serviceDto.response.AddStudentResponse;
import com.example.reactiveprogramming.serviceDto.response.GetStudentResponse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/student")
public class StudentController {

    private final IStudentService studentService;

    @PostMapping
    public Single<ResponseEntity<AddStudentWebResponse>> addStudent(@RequestBody AddStudentWebRequest AddStudentWebRequest){
        return studentService.addStudent(toAddStudentRequest(AddStudentWebRequest))
                .map(resp -> ResponseEntity.ok(toAddStudentsWebResponse(resp)));
    }

    private AddStudentWebResponse toAddStudentsWebResponse(AddStudentResponse resp) {
        AddStudentWebResponse addStudentWebResponse = new AddStudentWebResponse();
        BeanUtils.copyProperties(resp, addStudentWebResponse);
        return addStudentWebResponse;
    }

    private AddStudentRequest toAddStudentRequest(AddStudentWebRequest addStudentWebRequest) {
        AddStudentRequest studentRequest = new AddStudentRequest();
        BeanUtils.copyProperties(addStudentWebRequest, studentRequest);
        return studentRequest;
    }

    @PutMapping(value = "/{id}")
    public Completable updateStudent(@PathVariable("id") Long studentId,
                                     @RequestBody UpdateStudentWebRequest updateStudentWebRequest){
        return studentService.updateStudent(toUpdateStudentRequest(studentId, updateStudentWebRequest));
    }

    private UpdateStudentRequest toUpdateStudentRequest(Long studentId,
                                                        UpdateStudentWebRequest updateStudentWebRequest){
        UpdateStudentRequest studentRequest = new UpdateStudentRequest();
        BeanUtils.copyProperties(updateStudentWebRequest, studentRequest);
        studentRequest.setId(studentId);
        return studentRequest;
    }

    @GetMapping(value = "/{id}")
    public Single<ResponseEntity<GetStudentWebResponse>> getStudent(@PathVariable("id") Long studentId){
        return studentService.getStudent(studentId)
                .map(resp -> ResponseEntity.ok(toGetStudentWebResponse(resp)));
    }

    private GetStudentWebResponse toGetStudentWebResponse(GetStudentResponse resp) {
        GetStudentWebResponse getStudentWebResponse = new GetStudentWebResponse();
        BeanUtils.copyProperties(resp, getStudentWebResponse);
        return getStudentWebResponse;
    }

    @GetMapping(value = "/active")
    public Observable<List<GetStudentWebResponse>> getActiveStudents(){
        return studentService
                .getActiveStudents()
                .map(i -> i
                        .stream()
                        .map(this::toGetStudentWebResponse)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/{id}")
    public Completable deleteStudent(@PathVariable("id") Long studentId){
        return studentService.deleteStudent(studentId);
    }
}
