package com.example.demo384test.controller;


import com.example.demo384test.model.Permission;
import com.example.demo384test.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PermissionController {
    @Autowired
    private PermissionRepository permissionRepository;

    @PostMapping(path="/permissions/add")
    public @ResponseBody
    String addNewPermission (@RequestParam String name) {
        Permission n = new Permission();
        n.setName(name);
        permissionRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/permissions/all")
    public @ResponseBody Iterable<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @PostMapping("/process_add_permission")
    public ModelAndView processAddPermission(Permission permission) {
        permissionRepository.save(permission);
        return new ModelAndView("success");
    }
}