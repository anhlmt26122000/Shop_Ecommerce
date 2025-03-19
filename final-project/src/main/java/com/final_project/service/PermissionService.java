package com.final_project.service;

import com.final_project.dto.request.PermissionRequest;
import com.final_project.dto.response.PermissionResponse;
import com.final_project.dto.response.UserResponse;
import com.final_project.entity.Permission;
import com.final_project.entity.User;
import com.final_project.mapper.PermissionMapper;
import com.final_project.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = lombok.AccessLevel.PRIVATE)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    // Create Permission
    public PermissionResponse create(PermissionRequest request) {
        //Map request to permission
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        //Map permission to response
        return permissionMapper.toPermissionResponse(permission);
    }

    //Get All Permission
    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    //Delete Permission
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }

}
