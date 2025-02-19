package com.example.springconnectmysql.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    UNCATEGORIZED_EXCEPTION(999,"Uncategorized "),
    USERNAME_INVALID(103, "Username must be at least 5 characters"),
    PASSWORD_INVALID(104,"Password must be at least 8 characters"),
    INVALID_KEY(101, "Invalid message key"),
    NOT_FILL_INFOR(108,"You must fill all information"),
    IDUSER_NOT_EXIST(109, "User doesn't exist")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
