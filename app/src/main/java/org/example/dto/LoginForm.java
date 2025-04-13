package org.example.dto;
import lombok.*;

@Getter
@Setter
@ToString
public class LoginForm {

    private String id;

    private String name;

    private String role; // "user (student)" or "admin (teacher)"
}
