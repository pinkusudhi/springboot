package com.example.Springboot_pratice.dto;

import com.example.Springboot_pratice.Entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String gender;
    private String subject;
    private String employeeId;
    private Date hireDate;
    private double salary;
    private String department;

    private Long studentId;
}
