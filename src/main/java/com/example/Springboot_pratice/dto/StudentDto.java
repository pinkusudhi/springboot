package com.example.Springboot_pratice.dto;

import com.example.Springboot_pratice.Entity.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String gender;
    private String studentId;
    private double cgpa;


    private List<TeacherDto> teacher;

}
