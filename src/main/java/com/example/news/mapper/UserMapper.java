package com.example.news.mapper;

import com.example.news.model.User;
import com.example.news.web.dto.request.UserUpsertRequest;
import com.example.news.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(encode(request))")
    public abstract User requestToUser(UserUpsertRequest request);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", expression = "java(encode(request))")
    public abstract User requestToUser(Long userId, UserUpsertRequest request);

    public abstract UserResponse userToResponse(User user);

    public abstract List<UserResponse> userListToUserResponseList(List<User> users);

    protected String encode(UserUpsertRequest request) {
        return passwordEncoder.encode(request.getPassword());
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
