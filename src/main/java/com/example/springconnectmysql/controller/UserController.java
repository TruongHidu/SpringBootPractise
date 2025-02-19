package com.example.springconnectmysql.controller;

import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.request.ApiRespone;
import com.example.springconnectmysql.request.UserCreationRequest;
import com.example.springconnectmysql.request.UserUpdateRequest;
import com.example.springconnectmysql.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiRespone<User> createUser(@RequestBody @Valid UserCreationRequest request ){
        ApiRespone<User> apiRespone = new ApiRespone<>();
        apiRespone.setResult( userService.createUser(request));
        return apiRespone;
    }
    @GetMapping
    List<User> getUsers(){
        return userService.getUser();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }
    @PutMapping("/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);

    }
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
         userService.deleteUser(userId);
         return "User was deleted!";
    }

}
