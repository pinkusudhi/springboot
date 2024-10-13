package com.example.Springboot_pratice.CustomResponce;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponce<T> {

    public String message;
    public T payload;
    public HttpStatus status;
    public CustomResponce(String message, T payload, HttpStatus status) {
        this.message=  message;
        this.payload = payload;
        this.status = status;
    }
}
