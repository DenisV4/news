package com.example.news.component;

import com.example.news.exception.ResourceNotFoundException;
import com.example.news.model.User;
import com.example.news.service.UserService;
import com.example.news.web.controller.UserController;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMethodAccessInspector extends AbstractMethodAccessInspector<UserController> {
    private final UserService userService;

    @PostConstruct
    public void init() {
        setControllerClass(UserController.class);
    }

    @Override
    public boolean inspect(Long resourceId, Long userId) {
        User user;
        try {
            user = userService.findById(resourceId);
        } catch (ResourceNotFoundException e) {
            return true;
        }

        return user.getId().equals(userId);
    }
}
