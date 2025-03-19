package com.final_project.service;

import com.final_project.dto.request.RoleRequest;
import com.final_project.dto.response.RoleResponse;
import com.final_project.entity.Role;
import com.final_project.mapper.RoleMapper;
import com.final_project.repository.PermissionRepository;
import com.final_project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = lombok.AccessLevel.PRIVATE)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        //Map request to role
        Role role = roleMapper.toRole(request);
        roleRepository.save(role);
        //Map role to response
        return roleMapper.toRoleResponse(role);
    }
}
