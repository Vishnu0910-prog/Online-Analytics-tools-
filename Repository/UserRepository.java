package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

    List<UserModel> findAllByRoleIsNull();
}
