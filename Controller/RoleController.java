package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.Role;
import com.grd.online.paper.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "")
    private ResponseEntity<ResponseBean> findAll() {
        ResponseBean response = roleService.findAll();
        response.setMessage("All roles retrieved"); // Set or override the message
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "")
    private ResponseEntity<ResponseBean> save(@RequestBody Role role) {
        ResponseBean response = roleService.save(role);
        response.setMessage("Role saved successfully!"); // Set or override the message
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}