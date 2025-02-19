package com.example.springconnectmysql.mapper;

import com.example.springconnectmysql.dto.respone.UserRespone;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.dto.request.UserCreationRequest;
import com.example.springconnectmysql.dto.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserRespone toUserRespone(User user);
    void updateUser (@MappingTarget User user, UserUpdateRequest request);

}
