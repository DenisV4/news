package com.example.news.service;

import com.example.news.model.Role;
import com.example.news.model.User;
import com.example.news.web.dto.request.Pagination;

import java.util.List;

public interface UserService {

    List<User> findAll(Pagination pagination);

    User findById(Long id);

    User findByName(String name);

    User save(User user, List<Role> roles);

    User update(User user);

    void deleteById(Long id);
}
