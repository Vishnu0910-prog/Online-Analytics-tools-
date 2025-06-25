package com.grd.online.paper.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grd.online.paper.Bean.LoginRequest;
import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.UserModel;
import com.grd.online.paper.Service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseBean> login(@RequestBody LoginRequest request) {
        System.out.println("Login Successful");
        ResponseBean response = userService.login(request);
        return new ResponseEntity<ResponseBean>(response, HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseBean> addOrUpdate(@RequestBody UserModel user) {
        ResponseBean response = userService.signUp(user);
        return new ResponseEntity<ResponseBean>(response, HttpStatus.OK);
    }

}