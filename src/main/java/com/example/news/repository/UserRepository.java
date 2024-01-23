package com.example.news.repository;

import com.example.news.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = {User.Fields.news, User.Fields.comments})
    @NonNull Page<User> findAll(@NonNull Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {User.Fields.news, User.Fields.comments})
    @NonNull Optional<User> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {User.Fields.roles})
    Optional<User> findByName(String name);
}
