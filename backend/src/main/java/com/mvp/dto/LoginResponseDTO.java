package com.mvp.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private int userId;
    private String username;
    private String email;
    private Long expiresIn;
    private String msg;
}
