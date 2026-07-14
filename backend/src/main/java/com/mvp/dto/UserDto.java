package com.mvp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String msg;

}
