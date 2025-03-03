package com.example.springconnectmysql.dto.request;

import com.example.springconnectmysql.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String userName;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @NotBlank(message = "NOT_FILL_INFOR")
    String firstName, lastName;
    @DobConstraint(min = 16, message = "DOB_NOT_ENOUGH")
    LocalDate dob;


}
