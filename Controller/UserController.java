package com.grd.online.paper.Controller;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.UserModel;
import com.grd.online.paper.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    private ResponseEntity<ResponseBean> findAllUser() {
        ResponseBean response = userService.findAll();
        response.setMessage("All users retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/updateRole")
    private ResponseEntity<ResponseBean> updateUserRole(@RequestBody UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserRole(user));
    }

    @GetMapping(path = "/nonRole")
    private ResponseEntity<ResponseBean> findAllByNonRole() {
        ResponseBean response = userService.findAllWithoutRole();
        if (response.getData() == null || ((List<?>) response.getData()).isEmpty()) {
            response.setMessage("There are no non-role users in the system");
        } else {
            response.setMessage("Non-role users retrieved successfully.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserModel> getUser(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }
}
