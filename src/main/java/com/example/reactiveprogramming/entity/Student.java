package com.example.reactiveprogramming.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "students")
@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "active")
    private boolean active = true;

}
