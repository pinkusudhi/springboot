package com.example.Springboot_pratice.Repo;

import com.example.Springboot_pratice.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {

}
