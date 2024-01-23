package com.example.news.service.impl;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.Role;
import com.example.news.model.User;
import com.example.news.repository.UserRepository;
import com.example.news.service.UserService;
import com.example.news.util.BeanUtils;
import com.example.news.web.dto.request.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll(Pagination pagination) {
        var pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        return userRepository.findAll(pageRequest).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException.supply("User with id={0} not found", id));
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User save(User user, List<Role> roles) {
        var newUser = userRepository.save(user);
        roles.forEach(role -> role.setUser(newUser));
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    @Override
    public User update(User user) {
        var existingUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existingUser);
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
