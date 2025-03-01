package com.example.springconnectmysql.dto.request;

import com.example.springconnectmysql.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserUpdateRequest {
    String password;
    String firstName, lastName;
    @DobConstraint(min = 16, message = "DOB_NOT_ENOUGH")
    LocalDate dob;
    List<String> roles;

}
