package com.example.demo384test.controller;


import com.example.demo384test.model.Permission;
import com.example.demo384test.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/permissions")
public class PermissionController {
    @Autowired
    private PermissionRepository permissionRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewPermission (@RequestParam String name) {
        Permission n = new Permission();
        n.setName(name);
        permissionRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}