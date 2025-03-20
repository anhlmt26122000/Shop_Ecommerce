package com.final_project.mapper;

import com.final_project.dto.request.UserCreationRequest;
import com.final_project.dto.request.UserUpdateRequest;
import com.final_project.dto.response.UserResponse;
import com.final_project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
