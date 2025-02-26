package com.example.springconnectmysql.controller;

import com.example.springconnectmysql.dto.request.ApiRespone;
import com.example.springconnectmysql.dto.request.PermissionRequest;
import com.example.springconnectmysql.dto.respone.PermissionResponse;
import com.example.springconnectmysql.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ApiRespone<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiRespone.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiRespone<List<PermissionResponse>> getAll() {
        return ApiRespone.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    public ApiRespone<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiRespone.<Void>builder().build();
    }
}
