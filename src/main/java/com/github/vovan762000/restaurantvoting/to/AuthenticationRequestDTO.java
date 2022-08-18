package com.github.vovan762000.restaurantvoting.to;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
