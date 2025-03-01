package com.example.springconnectmysql.mapper;

import com.example.springconnectmysql.dto.respone.UserRespone;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.dto.request.UserCreationRequest;
import com.example.springconnectmysql.dto.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //@Mapping(source = "firstName", target = "lastName")
    UserRespone toUserRespone(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser (@MappingTarget User user, UserUpdateRequest request);

}
