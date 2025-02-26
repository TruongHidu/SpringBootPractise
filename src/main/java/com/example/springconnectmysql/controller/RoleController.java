package com.example.springconnectmysql.controller;

import com.example.springconnectmysql.dto.request.ApiRespone;
import com.example.springconnectmysql.dto.request.PermissionRequest;
import com.example.springconnectmysql.dto.request.RoleRequest;
import com.example.springconnectmysql.dto.respone.PermissionResponse;
import com.example.springconnectmysql.dto.respone.RoleResonse;
import com.example.springconnectmysql.service.PermissionService;
import com.example.springconnectmysql.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiRespone<RoleResonse> create(@RequestBody RoleRequest request) {
        return ApiRespone.<RoleResonse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiRespone<List<RoleResonse>> getAll() {
        return ApiRespone.<List<RoleResonse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiRespone<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiRespone.<Void>builder().build();
    }
}
