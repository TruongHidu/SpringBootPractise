package com.example.springconnectmysql.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class UserCreationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    private String userName;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    @NotBlank(message = "NOT_FILL_INFOR")
    private String firstName, lastName;
    private LocalDate dob;


}
