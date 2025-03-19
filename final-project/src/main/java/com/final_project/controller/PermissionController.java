package com.final_project.controller;

import com.final_project.dto.request.PermissionRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.dto.response.PermissionResponse;
import com.final_project.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
       return ApiResponse.<PermissionResponse>builder()
               .result(permissionService.create(request))
               .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<String> delete(@PathVariable("permission") String permission) {
        permissionService.delete(permission);
        return ApiResponse.<String>builder()
                .result("Permission deleted")
                .build();
    }
}
