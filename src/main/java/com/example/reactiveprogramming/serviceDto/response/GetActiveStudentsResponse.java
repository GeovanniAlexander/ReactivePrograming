package com.example.reactiveprogramming.serviceDto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetActiveStudentsResponse {
    private String name;
    private Integer age;
}
