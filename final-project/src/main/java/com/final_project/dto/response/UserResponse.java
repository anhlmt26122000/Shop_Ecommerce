package com.final_project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.final_project.entity.Role;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    String address;
    Set<Role> roles;
}
