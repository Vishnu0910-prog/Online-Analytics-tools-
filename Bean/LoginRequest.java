package com.grd.online.paper.Bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginRequest {
    private String username;
    private String password;
}
