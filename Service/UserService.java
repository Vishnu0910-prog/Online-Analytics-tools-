package com.grd.online.paper.Service;

import com.grd.online.paper.PROPERTIES;
import com.grd.online.paper.Bean.LoginRequest;
import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.CommonException.GlobalException;
import com.grd.online.paper.Model.Role;
import com.grd.online.paper.Model.UserModel;
import com.grd.online.paper.Repository.RoleRepository;
import com.grd.online.paper.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public ResponseBean findAll() {
        return ResponseBean.builder()
                .data(userRepository.findAll())
                .build();
    }

    public ResponseBean findAllWithoutRole() {
        return ResponseBean.builder()
                .data(userRepository.findAllByRoleIsNull())
                .build();
    }

    @Transactional
    public ResponseBean updateUserRole(UserModel user) {
        UserModel isExist = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new GlobalException.NotFound(PROPERTIES.MESSAGE.userNotFound));
        if (isExist != null) {
            Role role = roleRepository.findById(user.getRole().getRoleId())
                    .orElseThrow(() -> new GlobalException.NotFound("Invalid role"));
            isExist.setRole(role);
        }
        return ResponseBean.builder()
                .data(isExist)
                .message(PROPERTIES.MESSAGE.roleUpdateSuccess)
                .build();
    }

    public ResponseBean login(LoginRequest loginRequest) {

        if (loginRequest != null && loginRequest.getUsername() != null && loginRequest.getPassword() != null) {

            UserModel user = findByUsername(loginRequest.getUsername());
            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                user.setLastLoginTime(new Date());
                user.setIsLoggedIn(true);
                saveUser(user);

                Map<String, Object> res = new HashMap<String, Object>();
                res.put("username", user.getUsername());
                res.put("email", user.getEmail());
                res.put("userId", user.getUserId());
                res.put("role", user.getRole());

                return ResponseBean.builder()
                        .data(res)
                        .message(PROPERTIES.MESSAGE.loginSuccess)
                        .build();
            }
            throw new GlobalException.UnAuthorized(PROPERTIES.MESSAGE.invalidCredentials);
        }
        throw new GlobalException.UnAuthorized(PROPERTIES.MESSAGE.requiredUserAndPassword);
    }

    public ResponseBean signUp(UserModel user) {
        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            UserModel existingUser = findByUsername(user.getUsername());

            if (existingUser != null)
                throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.userAlreadyExists);

            saveUser(user);
            return ResponseBean.builder().message(PROPERTIES.MESSAGE.signUpSuccess).build();
        }
        throw new GlobalException.BadRequest(PROPERTIES.MESSAGE.invalidDetails);
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}