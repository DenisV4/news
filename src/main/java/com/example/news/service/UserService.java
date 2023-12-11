package com.example.news.service;

import com.example.news.model.User;
import com.example.news.web.dto.request.Pagination;

import java.util.List;

public interface UserService {

    List<User> findAll(Pagination pagination);

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);
}
