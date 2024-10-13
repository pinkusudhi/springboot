package com.example.Springboot_pratice.Service;


import com.example.Springboot_pratice.CustomResponce.CustomResponce;
import com.example.Springboot_pratice.Entity.Student;
import com.example.Springboot_pratice.Entity.Teacher;
import com.example.Springboot_pratice.Repo.StudentRepo;
import com.example.Springboot_pratice.dto.StudentDto;
import com.example.Springboot_pratice.dto.TeacherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Studentservice {

    private static final Logger log = LoggerFactory.getLogger(Studentservice.class);
    @Autowired
    public StudentRepo studentRepo;


    public StudentDto saveStudent(StudentDto studentDto)
    {
        Student student=new Student();
        student.setName(studentDto.getName());
        student.setStudentId(studentDto.getStudentId());
        student.setAddress(studentDto.getAddress());
        student.setGender(studentDto.getGender());
        student.setCgpa(studentDto.getCgpa());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        student.setEmail(studentDto.getEmail());
        student.setDateOfBirth(studentDto.getDateOfBirth());
        Student saveStudent=studentRepo.save(student);
        log.info("Save into the db"+student);
        StudentDto setResponce=new StudentDto();
        BeanUtils.copyProperties(saveStudent,setResponce);
        return setResponce;
    }
    public StudentDto getByStudentId(Long id) {
        Optional<Student> studentOpt = studentRepo.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            List<TeacherDto> teacherDtos = student.getTeacher().stream().map(teacher -> {
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setName(teacher.getName());
                teacherDto.setEmail(teacher.getEmail());
                teacherDto.setPhoneNumber(teacher.getPhoneNumber());
                teacherDto.setAddress(teacher.getAddress());
                teacherDto.setDateOfBirth(teacher.getDateOfBirth());
                teacherDto.setGender(teacher.getGender());
                teacherDto.setSubject(teacher.getSubject());
                teacherDto.setEmployeeId(teacher.getEmployeeId());
                teacherDto.setHireDate(teacher.getHireDate());
                teacherDto.setSalary(teacher.getSalary());
                teacherDto.setDepartment(teacher.getDepartment());

                return teacherDto;
            }).collect(Collectors.toList());
            StudentDto studentDto = new StudentDto();
            studentDto.setName(student.getName());
            studentDto.setEmail(student.getEmail());
            studentDto.setPhoneNumber(student.getPhoneNumber());
            studentDto.setAddress(student.getAddress());
            studentDto.setDateOfBirth(student.getDateOfBirth());
            studentDto.setGender(student.getGender());
            studentDto.setCgpa(student.getCgpa());
            studentDto.setTeacher(teacherDtos);
            return studentDto;
        }
        return null;
    }
    public StudentDto GetById(Long id) {
        Optional<Student> studentOpt = studentRepo.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            StudentDto studentDto = new StudentDto();
            studentDto.setName(student.getName());
            studentDto.setEmail(student.getEmail());
            studentDto.setPhoneNumber(student.getPhoneNumber());
            studentDto.setAddress(student.getAddress());
            studentDto.setDateOfBirth(student.getDateOfBirth());
            studentDto.setGender(student.getGender());
            studentDto.setCgpa(student.getCgpa());
            studentDto.setStudentId(student.getStudentId());
            return studentDto;
        }

        return null;
    }
}
