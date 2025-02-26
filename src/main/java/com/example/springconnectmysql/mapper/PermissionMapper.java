package com.example.springconnectmysql.mapper;

import com.example.springconnectmysql.dto.request.PermissionRequest;
import com.example.springconnectmysql.dto.request.UserCreationRequest;
import com.example.springconnectmysql.dto.request.UserUpdateRequest;
import com.example.springconnectmysql.dto.respone.PermissionResponse;
import com.example.springconnectmysql.dto.respone.UserRespone;
import com.example.springconnectmysql.entity.Permission;
import com.example.springconnectmysql.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);


}
