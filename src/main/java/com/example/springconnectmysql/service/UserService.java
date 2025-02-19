package com.example.springconnectmysql.service;

import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.exception.AppException;
import com.example.springconnectmysql.exception.ErrorCode;
import com.example.springconnectmysql.repository.UserRepository;
import com.example.springconnectmysql.request.UserCreationRequest;
import com.example.springconnectmysql.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByUserName(request.getUserName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public User updateUser(String userId, UserUpdateRequest request){
        User user = getUser(userId);
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setDob(request.getDob());
        return userRepository.save(user);

    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
    public List<User> getUser(){
        return userRepository.findAll();
    }
    public User getUser(String id){
        return userRepository.findById(id).orElseThrow( ()-> new RuntimeException("User not found!")) ;
    }






}
