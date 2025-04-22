package com.final_project.controller;

import com.final_project.dto.response.ApiResponse;
import com.final_project.dto.request.UserCreationRequest;
import com.final_project.dto.request.UserUpdateRequest;
import com.final_project.dto.response.UserResponse;
import com.final_project.entity.User;
import com.final_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getUsers());
        return response;
    }

    @GetMapping("/{username}")
    ApiResponse<UserResponse> getUser(@PathVariable("username") String username){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUser(username));
        return response;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getMyInfo());
        return response;
    }

    @PutMapping("/{userID}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userID") String userID, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.updateUser(userID, request));
        return response;
    }

    @DeleteMapping("/{userID}")
    ApiResponse<String> deleteUser(@PathVariable("userID") String userID){
        ApiResponse<String> response = new ApiResponse<>();
        userService.deleteUser(userID);
        response.setResult("User deleted");
        return response;
    }

    @GetMapping("/page")
    public ApiResponse<Page<UserResponse>> getUsersWithPagination(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        page = page-1;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserResponse> userPage = userService.getUsersWithPagination(pageRequest);
        return new ApiResponse<>(200, "User list retrieved successfully", userPage);
    }
}
