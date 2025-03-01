package com.example.springconnectmysql.dto.respone;

import com.example.springconnectmysql.entity.Role;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRespone {
    String id;
    String userName;
    String firstName, lastName;
    LocalDate dob;

    Set<RoleResonse> roles;
}
