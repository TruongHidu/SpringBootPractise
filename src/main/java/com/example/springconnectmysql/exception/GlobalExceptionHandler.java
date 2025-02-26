package com.example.springconnectmysql.exception;

import com.example.springconnectmysql.dto.request.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiRespone> handingRuntimeException(RuntimeException exception){
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiRespone.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiRespone);
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRespone> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiRespone.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
             errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e){


        }

        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);


    }
}
