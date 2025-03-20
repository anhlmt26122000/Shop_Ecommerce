package com.final_project.service;

import com.final_project.dto.request.UserCreationRequest;
import com.final_project.dto.request.UserUpdateRequest;
import com.final_project.dto.response.UserResponse;
import com.final_project.entity.Role;
import com.final_project.entity.User;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.mapper.UserMapper;
import com.final_project.repository.RoleRepository;
import com.final_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;


    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        // Map request -> User
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById("USER").ifPresent(roles ::add);
        user.setRoles(roles);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    // Get User hien tai dang dang nhap
    public UserResponse getMyInfo(){
        var context=SecurityContextHolder.getContext();
        String name= context.getAuthentication().getName();
        User user =userRepository.findByUsername(name)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }


    public UserResponse updateUser(String userID, UserUpdateRequest request) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Map request -> User
        userMapper.updateUser(user, request);
        // Encrypt password if provided
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles= roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        User updatedUser = userRepository.save(user);
        // Map User -> UserResponse
        return userMapper.toUserResponse(updatedUser);
    }


    public void deleteUser(String userID){
        userRepository.deleteById(userID);
    }

    public Page<User> getUsersWithPagination(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
