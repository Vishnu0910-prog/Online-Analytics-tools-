package com.grd.online.paper.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private Date registeredDate = new Date();

    private Date lastLoginTime;

    private Boolean isLoggedIn;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = true)
    private Role role = null;
}
