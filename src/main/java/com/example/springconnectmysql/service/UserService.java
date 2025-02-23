package com.example.springconnectmysql.service;

import com.example.springconnectmysql.dto.respone.UserRespone;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.enums.Role;
import com.example.springconnectmysql.exception.AppException;
import com.example.springconnectmysql.exception.ErrorCode;
import com.example.springconnectmysql.mapper.UserMapper;
import com.example.springconnectmysql.repository.UserRepository;
import com.example.springconnectmysql.dto.request.UserCreationRequest;
import com.example.springconnectmysql.dto.request.UserUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    @Transactional
    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUserName(request.getUserName().trim())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles= new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public UserRespone updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.IDUSER_NOT_EXIST));

        userMapper.updateUser(user, request);
        return userMapper.toUserRespone(userRepository.save(user));

    }
    public void deleteUser(String userId){

        if(!userRepository.existsById(userId))
            throw new AppException(ErrorCode.IDUSER_NOT_EXIST);
        userRepository.deleteById(userId);
    }
    public List<User> getUser(){
        return userRepository.findAll();
    }
    public UserRespone getUser(String id){
        return userMapper.toUserRespone(userRepository.findById(id).orElseThrow( ()-> new AppException(ErrorCode.IDUSER_NOT_EXIST)));
    }






}
