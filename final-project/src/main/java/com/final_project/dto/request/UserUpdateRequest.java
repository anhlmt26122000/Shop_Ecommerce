package com.final_project.dto.request;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_AT_LEAST_8")
    String password;

    @Size(min=1, message = "EMAIL_NULL")
    String email;

    @Size(min=1, message = "FIRST_NAME_NULL")
    String firstName;

    @Size(min=1, message = "LAST_NAME_NULL")
    String lastName;
    String address;
    List<String> roles;
}
