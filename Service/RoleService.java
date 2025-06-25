package com.grd.online.paper.Service;

import com.grd.online.paper.Bean.ResponseBean;
import com.grd.online.paper.Model.Role;
import com.grd.online.paper.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ResponseBean findAll() {
        return ResponseBean.builder()
                .data(roleRepository.findAll())
                .build();
    }

    public ResponseBean save(Role role) {
        return ResponseBean.builder()
                .data(roleRepository.save(role))
                .build();
    }
}
