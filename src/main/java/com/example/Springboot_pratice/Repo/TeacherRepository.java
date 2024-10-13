package com.example.Springboot_pratice.Repo;

import com.example.Springboot_pratice.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
