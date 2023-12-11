package com.example.news.mapper;

import com.example.news.model.User;
import com.example.news.web.dto.request.UserUpsertRequest;
import com.example.news.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User requestToUser(UserUpsertRequest request);

    @Mapping(target = "id", source = "userId")
    User requestToUser(Long userId, UserUpsertRequest request);

    UserResponse userToResponse(User user);

    List<UserResponse> userListToUserResponseList(List<User> users);
}
