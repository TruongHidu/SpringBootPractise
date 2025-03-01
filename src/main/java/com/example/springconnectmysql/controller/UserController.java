package com.example.springconnectmysql.controller;

import com.example.springconnectmysql.dto.respone.UserRespone;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.dto.request.ApiRespone;
import com.example.springconnectmysql.dto.request.UserCreationRequest;
import com.example.springconnectmysql.dto.request.UserUpdateRequest;
import com.example.springconnectmysql.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiRespone<UserRespone> createUser(@RequestBody @Valid UserCreationRequest request ){
        return ApiRespone.<UserRespone>builder()
                .result(userService.createUser(request))
                .build();
    }
    @GetMapping
    ApiRespone<List<UserRespone>> getUsers(){
        var authetication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username:{}", authetication.getName());
        authetication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiRespone.<List<UserRespone>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    UserRespone getUser(@PathVariable("userId") String userId){

        return userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    ApiRespone<UserRespone> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return ApiRespone.<UserRespone>builder()
                .result(userService.updateUser(userId, request))
                .build();

    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
         userService.deleteUser(userId);
         return "User was deleted!";
    }
    @GetMapping("/myInfo")
    ApiRespone<UserRespone> getMyInfo(){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getMyInfo())
                .build();
    }

}
