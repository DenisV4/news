package com.example.news.web.dto.request;

import com.example.news.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserUpsertRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 30, message = "Name length must be less than {min} and greater than {max}")
    String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password should not be blank")
    String password;

    @NotNull(message = "Roles is required")
    List<Role.RoleType> roles;
}
