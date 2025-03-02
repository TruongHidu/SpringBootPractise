package com.example.springconnectmysql.repository;

import com.example.springconnectmysql.entity.InvalidatedToken;
import com.example.springconnectmysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,String> {



}
