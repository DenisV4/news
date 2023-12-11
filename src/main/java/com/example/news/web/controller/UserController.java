package com.example.news.web.controller;

import com.example.news.mapper.UserMapper;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.Pagination;
import com.example.news.web.dto.request.UserUpsertRequest;
import com.example.news.web.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAll(@Valid Pagination pagination) {
        var users = userService.findAll(pagination);
        return userMapper.userListToUserResponseList(users);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable
                            @Positive(message = "ID must be greater than 0")
                            Long id) {

        var user = userService.findById(id);
        return userMapper.userToResponse(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserUpsertRequest request) {
        var user = userService.save(userMapper.requestToUser(request));
        return userMapper.userToResponse(user);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id,
                               @Valid @RequestBody UserUpsertRequest request) {

        var user = userService.update(userMapper.requestToUser(id, request));
        return userMapper.userToResponse(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id) {

        userService.deleteById(id);
    }
}
