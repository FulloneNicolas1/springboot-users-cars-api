package com.api.demo.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "firstName requested")
    private String firstName;

    @NotBlank(message = "lastName requested")
    private String lastName;

    @NotBlank(message = "email requested")
    @Email(message = "email invalid")
    private String email;
}