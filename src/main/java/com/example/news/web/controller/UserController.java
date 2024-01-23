package com.example.news.web.controller;

import com.example.news.aspect.UserRoleRestriction;
import com.example.news.mapper.RoleMapper;
import com.example.news.mapper.UserMapper;
import com.example.news.service.UserService;
import com.example.news.web.dto.request.Pagination;
import com.example.news.web.dto.request.UserUpsertRequest;
import com.example.news.web.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final RoleMapper roleMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> getAll(@Valid Pagination pagination) {
        var users = userService.findAll(pagination);
        return userMapper.userListToUserResponseList(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @UserRoleRestriction
    public UserResponse get(@PathVariable
                            @Positive(message = "ID must be greater than 0")
                            Long id,
                            @AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.findById(id);
        return userMapper.userToResponse(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserUpsertRequest request) {
        var user = userMapper.requestToUser(request);
        var roles = roleMapper.roleListFromRoleTypeList(request.getRoles());
        var createdUser = userService.save(user, roles);

        return userMapper.userToResponse(createdUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @UserRoleRestriction
    public UserResponse update(@PathVariable
                               @Positive(message = "ID must be greater than 0")
                               Long id,
                               @Valid @RequestBody
                               UserUpsertRequest request,
                               @AuthenticationPrincipal UserDetails userDetails) {

        var user = userService.update(userMapper.requestToUser(id, request));
        return userMapper.userToResponse(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @UserRoleRestriction
    public void delete(@PathVariable
                       @Positive(message = "ID must be greater than 0")
                       Long id,
                       @AuthenticationPrincipal UserDetails userDetails) {

        userService.deleteById(id);
    }
}
