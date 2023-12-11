package com.example.news.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpsertRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 30, message = "Name length must be less than {min} and greater than {max}")
    String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;
}
