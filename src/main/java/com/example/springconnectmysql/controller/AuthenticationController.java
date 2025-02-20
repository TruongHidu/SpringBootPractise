package com.example.springconnectmysql.controller;

import com.example.springconnectmysql.dto.request.ApiRespone;
import com.example.springconnectmysql.dto.request.AuthenticationRequest;
import com.example.springconnectmysql.dto.respone.AuthenticationRespone;
import com.example.springconnectmysql.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ApiRespone<AuthenticationRespone> authenticate(@RequestBody AuthenticationRequest request){
        boolean result = authenticationService.authenticate(request);
        return ApiRespone.<AuthenticationRespone>builder()
                .result(AuthenticationRespone.builder().authenticated(result)
                    .build())
                .build();

    }


}
