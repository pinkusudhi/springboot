package com.example.Springboot_pratice.Validation;

import com.example.Springboot_pratice.dto.TeacherDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TeacherValidation {

    public static String validTeacher(String message, String object) {
        if (object != null && !object.trim().isEmpty()) {
            return "Success";
        }
        return message + " cannot be null";
    }

    public static String validEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (email != null && pattern.matcher(email).matches()) {
            return "Success";
        }
        return "Invalid email format";
    }

    public static String validPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{10}$";  // Matches exactly 10 digits
        Pattern pattern = Pattern.compile(phoneRegex);

        if (phoneNumber != null && pattern.matcher(phoneNumber).matches()) {
            return "Success";
        }
        return "Phone number must be 10 digits";
    }

    public static String validSalary(String message, double salary) {
        if (salary > 0) {
            return "Success";
        }
        return message + " must be greater than 0";
    }

    public static String validDateOfBirth(Date dateOfBirth) {
        if (dateOfBirth != null) {
            return "Success";
        }
        return "Date of Birth cannot be null";
    }

    public static String isValidate(List<TeacherDto> teacherDtoList) {
        String validate = null;
        for (TeacherDto teacherDto : teacherDtoList) {
            // Validate teacher name
            validate = validTeacher("Teacher name", teacherDto.getName());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate email
            validate = validEmail(teacherDto.getEmail());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate phone number
            validate = validPhoneNumber(teacherDto.getPhoneNumber());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate address
            validate = validTeacher("Address", teacherDto.getAddress());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate date of birth
            validate = validDateOfBirth(teacherDto.getDateOfBirth());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate gender
            validate = validTeacher("Gender", teacherDto.getGender());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate subject
            validate = validTeacher("Subject", teacherDto.getSubject());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate employee ID
            validate = validTeacher("Employee ID", teacherDto.getEmployeeId());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate salary
            validate = validSalary("Salary", teacherDto.getSalary());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }

            // Validate department
            validate = validTeacher("Department", teacherDto.getDepartment());
            if (!validate.equalsIgnoreCase("Success")) {
                return validate;
            }
        }

        return "Success";
    }

}
