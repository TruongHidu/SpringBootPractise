package com.example.springconnectmysql.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userName;
    String password;
    String firstName, lastName;
    LocalDate dob;
    @ManyToMany
    Set<Role> roles;





}