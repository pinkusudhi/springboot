package com.example.Springboot_pratice.Controller;

import com.example.Springboot_pratice.CustomResponce.CustomResponce;
import com.example.Springboot_pratice.Service.Studentservice;
import com.example.Springboot_pratice.Validation.StudentValidation;
import com.example.Springboot_pratice.dto.StudentDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

@RestController
public class StrudentController {

    @Autowired
    public Studentservice studentservice;


    @PostMapping("Create")
    public  CustomResponce saveStudent(@RequestBody StudentDto studentDto)
    {
        CustomResponce customResponce=null;
        String validation=StudentValidation.studentvalidate(studentDto);
        if (!validation.equals("Success"))
        {
           return new CustomResponce<>(validation,null,null);
        }
        StudentDto studentData = studentservice.saveStudent(studentDto);
        customResponce = studentData != null
                ? new CustomResponce<>("Student data saved successfully", studentData, HttpStatus.CREATED)
                : new CustomResponce<>("Student data not set", studentData, HttpStatus.NOT_FOUND);
        return customResponce;
    }
    @GetMapping("Get")
    public ResponseEntity<CustomResponce> getTeacherBustudentId(@RequestParam Long id, HttpServletResponse response) {
        CustomResponce customResponce = null;
        StudentDto studentDto = studentservice.getByStudentId(id);
        if (studentDto != null) {
            customResponce = new CustomResponce("Student data fetched successFully", studentDto, HttpStatus.OK);
            return new ResponseEntity<>(customResponce, HttpStatus.OK);
        } else {
            customResponce = new CustomResponce("Student data not fetched succesFully", studentDto, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(customResponce, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("ID")
    public StudentDto getById(@RequestParam Long id)
    {
        StudentDto studentDto=this.studentservice.GetById(id);
        if(studentDto!=null)
        {
           return studentDto;
        }
        return null;
    }
}
