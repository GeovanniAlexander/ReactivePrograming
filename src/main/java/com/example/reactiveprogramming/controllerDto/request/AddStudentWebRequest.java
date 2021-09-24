package com.example.reactiveprogramming.controllerDto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentWebRequest {
    private String name;
    private Integer age;
}
