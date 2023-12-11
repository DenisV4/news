package com.example.news.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldNameConstants
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToMany(mappedBy = News.Fields.user, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<News> news = new HashSet<>();

    @OneToMany(mappedBy = Comment.Fields.user, cascade = CascadeType.ALL)
    @OrderColumn
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();
}
