package com.example.springconnectmysql.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(999,"Uncategorized ", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(103, "Username must be at least {min} characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(104,"Password must be at least {min} characters",HttpStatus.BAD_REQUEST),
    INVALID_KEY(101, "Invalid message key", HttpStatus.BAD_REQUEST),
    NOT_FILL_INFOR(108,"You must fill all information", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(109, "User doesn't exist", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(202, "Unauthenticated",HttpStatus.UNAUTHORIZED ),
    UNAUTHORIZED(201, "You dont have permission!",HttpStatus.FORBIDDEN),
    DOB_NOT_ENOUGH(6969, "Your age must be at least {min}!", HttpStatus.BAD_REQUEST)

    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
