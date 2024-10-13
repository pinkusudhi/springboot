package com.example.Springboot_pratice.Validation;

import com.example.Springboot_pratice.dto.StudentDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class StudentValidation {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String validate(String message,String object)
    {
        if(object!=null && !object.trim().isEmpty())
        {
            return "Success";
        }
        return message+"Can not be null";
    }
    public static String validateEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (email != null && pattern.matcher(email).matches()) {
            return "Success";
        }
        return "Invalid email format";
    }
    public static  String PhoneNumberFormat(String phoneNu)
    {
        String phoneRegex = "^[0-9]{10}$";
        Pattern pattern=Pattern.compile(phoneRegex);
        if(phoneNu!=null && pattern.matcher(phoneNu).matches())
        {
            return "Success";
        }
        return "Phone nu is not valid";
    }
    public static String studentid(String studentID)
    {
        String StuIdRegex = "^[0-9]{5}$";
        Pattern pattern=Pattern.compile(StuIdRegex);
        if(studentID!=null && pattern.matcher(studentID).matches())
        {
            return "Success";
        }
        else {
            return "Student id is Not Valid";
        }
    }
    public static String studentvalidate(StudentDto studentDto)
    {
        String valid =null;
        valid=validate("Student name",studentDto.getName());
                if(!valid.equalsIgnoreCase("Success")) {
                    return valid;
                }
        valid = validateEmailFormat(studentDto.getEmail());
        if (!valid.equalsIgnoreCase("Success")) {
            return valid;
        }
        valid=PhoneNumberFormat(studentDto.getPhoneNumber());
        if(!valid.equalsIgnoreCase("Success"))
        {
            return valid;
        }
        valid=validate("Studet address",studentDto.getAddress());
        if(!valid.equalsIgnoreCase("Success"))
        {
            return valid;
        }
        valid=validate("Student Date of birth",String.valueOf(studentDto.getDateOfBirth()));
        if(!valid.equalsIgnoreCase("Success"))
        {
            return valid;
        }
        valid=validate("Student gender",studentDto.getGender());
        if(!valid.equalsIgnoreCase("Success"))
        {
            return valid;
        }

        valid=studentid(studentDto.getStudentId());
        if (!valid.endsWith("ess"))
        {
            return valid;
        }

        valid=validate("Student CGPA", String.valueOf(studentDto.getCgpa()));
        if(!valid.startsWith("Succ"))
        {
            return valid;
        }
        return "Success";
    }
}
