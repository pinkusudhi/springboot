package com.example.Springboot_pratice.Service;


import com.example.Springboot_pratice.Entity.Student;
import com.example.Springboot_pratice.Entity.Teacher;
import com.example.Springboot_pratice.Repo.StudentRepo;
import com.example.Springboot_pratice.Repo.TeacherRepository;
import com.example.Springboot_pratice.dto.StudentDto;
import com.example.Springboot_pratice.dto.TeacherDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Studentservice {

    private static final Logger log = LoggerFactory.getLogger(Studentservice.class);
    @Autowired
    public StudentRepo studentRepo;

    @Autowired
    public RedisService redisService;

    @Autowired
    public TeacherRepository teacherRepository;

    private static final String STUDENT_PREFIX = "STUDENT:";


    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setStudentId(studentDto.getStudentId());
        student.setAddress(studentDto.getAddress());
        student.setGender(studentDto.getGender());
        student.setCgpa(studentDto.getCgpa());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        student.setEmail(studentDto.getEmail());
        student.setDateOfBirth(studentDto.getDateOfBirth());
        Student saveStudent = studentRepo.save(student);
        log.info("Save into the db" + student);
        StudentDto setResponce = new StudentDto();
        BeanUtils.copyProperties(saveStudent, setResponce);
        return setResponce;
    }

    public StudentDto getByStudentId(Long id) {
        String studentid = String.valueOf(id);
        String cacheKey = STUDENT_PREFIX + studentid;
        log.info("Cache key :" + cacheKey);
        StudentDto studentDto2 = redisService.getDataFromCache(cacheKey);
        log.info("get data from cache");
        if (studentDto2 == null) {
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
                studentDto.setStudentId(student.getStudentId());
                studentDto.setTeacher(teacherDtos);
                redisService.saveDataInCache(cacheKey, studentDto);
                log.info("data saved in cache");
                return studentDto;
            }
        }
        return studentDto2;
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

    @Transactional
    public StudentDto UpdateStudentWithTeacher(StudentDto studentDto, Long studentId) {
        String key = String.valueOf(studentId);
        String cacheKey = STUDENT_PREFIX + key;
        StudentDto studentDto1 = redisService.getDataFromCache(cacheKey);
        log.info("get data from cache" + cacheKey);
        if (studentDto1 == null) {
            log.info("get data from database");
            // Step 1: Find the student by ID
            Optional<Student> studentOptional = this.studentRepo.findById(studentId);
            log.info("Student id: " + studentOptional);

            if (studentOptional.isPresent()) {
                // Step 2: Get the existing student entity
                Student studentEntity = studentOptional.get();
                studentEntity.setName(studentDto.getName());
                studentEntity.setEmail(studentDto.getEmail());
                studentEntity.setPhoneNumber(studentDto.getPhoneNumber());
                studentEntity.setAddress(studentDto.getAddress());
                studentEntity.setDateOfBirth(studentDto.getDateOfBirth());
                studentEntity.setGender(studentDto.getGender());
                studentEntity.setCgpa(studentDto.getCgpa());
                studentEntity.setStudentId(studentDto.getStudentId());

                // Step 3: Update the list of teachers
                List<Teacher> teacherEntities = new ArrayList<>();
                List<TeacherDto> teacherDtos = new ArrayList<>();
                for (TeacherDto teacherDto : studentDto.getTeacher()) {
                    if (teacherDto.getId() != null) {
                        Teacher teacherEntity = teacherRepository.findById(teacherDto.getId()).orElseThrow(null);
                        teacherEntity.setName(teacherDto.getName());
                        teacherEntity.setEmail(teacherDto.getEmail());
                        teacherEntity.setAddress(teacherDto.getAddress());
                        teacherEntity.setGender(teacherDto.getGender());
                        teacherEntity.setDateOfBirth(teacherDto.getDateOfBirth());
                        teacherEntity.setPhoneNumber(teacherDto.getPhoneNumber());
                        teacherEntity.setDepartment(teacherDto.getDepartment());
                        teacherEntity.setEmployeeId(teacherDto.getEmployeeId());
                        teacherEntity.setSubject(teacherDto.getSubject());
                        teacherEntity.setSalary(teacherDto.getSalary());
                        teacherEntity.setHireDate(teacherDto.getHireDate());
                        teacherEntities.add(teacherEntity);
                        teacherDtos.add(teacherDto);
                    } else {
                        log.error("Teacher id is null");
                    }
                }
                studentEntity.setTeacher(teacherEntities);
                // Step 4: Save the updated student entity
                Student student = this.studentRepo.save(studentEntity);
                // Step 5: Return the updated student dto
                StudentDto studentDto2 = new StudentDto();
                BeanUtils.copyProperties(student, studentDto2);
                studentDto2.setTeacher(teacherDtos);
                redisService.saveDataInCache(cacheKey, studentDto2);
                return studentDto2;
            }
        }
        return studentDto1;
    }
}