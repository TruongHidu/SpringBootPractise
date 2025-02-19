package com.example.springconnectmysql.repository;

import com.example.springconnectmysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUserName(String userName);

}
