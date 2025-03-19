package com.final_project.mapper;


import com.final_project.dto.request.PermissionRequest;
import com.final_project.dto.response.PermissionResponse;
import com.final_project.entity.Permission;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
