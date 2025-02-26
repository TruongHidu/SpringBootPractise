package com.example.springconnectmysql.service;

import com.example.springconnectmysql.dto.request.PermissionRequest;
import com.example.springconnectmysql.dto.request.RoleRequest;
import com.example.springconnectmysql.dto.respone.PermissionResponse;
import com.example.springconnectmysql.dto.respone.RoleResonse;
import com.example.springconnectmysql.entity.Permission;
import com.example.springconnectmysql.mapper.PermissionMapper;
import com.example.springconnectmysql.mapper.RoleMapper;
import com.example.springconnectmysql.repository.PermissionRepository;
import com.example.springconnectmysql.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResonse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }

    public List<RoleResonse> getAll(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
