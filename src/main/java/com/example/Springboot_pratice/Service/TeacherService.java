package com.example.Springboot_pratice.Service;

import com.example.Springboot_pratice.Entity.Student;
import com.example.Springboot_pratice.Entity.Teacher;
import com.example.Springboot_pratice.Repo.StudentRepo;
import com.example.Springboot_pratice.Repo.TeacherRepository;
import com.example.Springboot_pratice.dto.TeacherDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);
    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public StudentRepo studentRepo;



    @Transactional
    public List<TeacherDto> SaveTecherwithStudentId(List<TeacherDto> teacherDto) {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        for (TeacherDto teacherDto1 : teacherDto) {
            // Retrieve the existing student by ID
            Optional<Student> studentOpt = studentRepo.findById(teacherDto1.getStudentId());

            if (!studentOpt.isPresent()) {
                throw new RuntimeException("Student ID " + teacherDto1.getStudentId() + " is not present in the database.");
            }
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();

                // Create a new Teacher entity and set its properties
                Teacher teacher = new Teacher();
                teacher.setName(teacherDto1.getName());
                teacher.setEmail(teacherDto1.getEmail());
                teacher.setAddress(teacherDto1.getAddress());
                teacher.setGender(teacherDto1.getGender());
                teacher.setDateOfBirth(teacherDto1.getDateOfBirth());
                teacher.setPhoneNumber(teacherDto1.getPhoneNumber());
                teacher.setDepartment(teacherDto1.getDepartment());
                teacher.setEmployeeId(teacherDto1.getEmployeeId());
                teacher.setSubject(teacherDto1.getSubject());
                teacher.setSalary(teacherDto1.getSalary());
                teacher.setHireDate(teacherDto1.getHireDate());
                teacher.setStudent(student); // Set the existing student

                teachers.add(teacher);
            }
        }


        teacherRepository.saveAll(teachers);
        log.info("List of teachers saved");


        teacherDtos = teachers.stream().map(teacher -> {
            TeacherDto teacherResponseDto = new TeacherDto();
            teacherResponseDto.setName(teacher.getName());
            teacherResponseDto.setEmail(teacher.getEmail());
            teacherResponseDto.setAddress(teacher.getAddress());
            teacherResponseDto.setGender(teacher.getGender());
            teacherResponseDto.setDateOfBirth(teacher.getDateOfBirth());
            teacherResponseDto.setPhoneNumber(teacher.getPhoneNumber());
            teacherResponseDto.setDepartment(teacher.getDepartment());
            teacherResponseDto.setEmployeeId(teacher.getEmployeeId());
            teacherResponseDto.setSubject(teacher.getSubject());
            teacherResponseDto.setSalary(teacher.getSalary());
            teacherResponseDto.setStudentId(teacher.getStudent().getId());
            return teacherResponseDto;
        }).collect(Collectors.toList());

        return teacherDtos;
    }

}
