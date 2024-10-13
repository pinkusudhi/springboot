package com.example.Springboot_pratice.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String gender;
    private String studentId;
    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;
    private double cgpa;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    public List<Teacher> teacher;

    @PrePersist
    protected  void onenroll()
    {
        this.enrollmentDate=new Date();
    }

}
