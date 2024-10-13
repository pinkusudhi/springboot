package com.example.Springboot_pratice.Controller;

import com.example.Springboot_pratice.CustomResponce.CustomResponce;
import com.example.Springboot_pratice.Service.TeacherService;
import com.example.Springboot_pratice.Validation.TeacherValidation;
import com.example.Springboot_pratice.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("save")
    public ResponseEntity<CustomResponce> SaveTeacherwithStudentId(
            @RequestBody List<TeacherDto> teacherDto)
    {


        // Validate teacher DTOs
        String validation = TeacherValidation.isValidate(teacherDto);
        if (!validation.equalsIgnoreCase("Success")) {
            CustomResponce<String> customResponce = new CustomResponce<>(validation, null, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(customResponce, HttpStatus.BAD_REQUEST);
        }

        // Call the service to save teachers
        List<TeacherDto> teacherDtos = teacherService.SaveTecherwithStudentId(teacherDto);
        if (teacherDtos != null && !teacherDtos.isEmpty()) {
            CustomResponce<List<TeacherDto>> customResponce = new CustomResponce<>("Teachers created successfully", teacherDtos, HttpStatus.CREATED);
            return new ResponseEntity<>(customResponce, HttpStatus.CREATED);
        } else {
            CustomResponce<List<TeacherDto>> customResponce = new CustomResponce<>("No teachers created", null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(customResponce, HttpStatus.NOT_FOUND);
        }
    }

}
