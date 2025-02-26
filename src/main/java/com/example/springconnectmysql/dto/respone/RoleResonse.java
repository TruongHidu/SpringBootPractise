package com.example.springconnectmysql.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResonse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
