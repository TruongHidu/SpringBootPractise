package com.example.springconnectmysql.dto.request;

import com.example.springconnectmysql.entity.Permission;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class RoleRequest {
    String name;
    String description;
    Set<String> permissions;



}
