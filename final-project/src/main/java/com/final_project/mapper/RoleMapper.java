package com.final_project.mapper;


import com.final_project.dto.request.RoleRequest;


import com.final_project.dto.response.RoleResponse;
import com.final_project.entity.Role;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest request);
}
