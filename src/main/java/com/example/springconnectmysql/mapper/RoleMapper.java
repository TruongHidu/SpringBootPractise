package com.example.springconnectmysql.mapper;

import com.example.springconnectmysql.dto.request.PermissionRequest;
import com.example.springconnectmysql.dto.request.RoleRequest;
import com.example.springconnectmysql.dto.respone.PermissionResponse;
import com.example.springconnectmysql.dto.respone.RoleResonse;
import com.example.springconnectmysql.entity.Permission;
import com.example.springconnectmysql.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResonse toRoleResponse(Role role);


}
