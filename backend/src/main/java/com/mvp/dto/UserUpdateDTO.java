package com.mvp.dto;

import jakarta.validation.constraints.Email;

public class UserUpdateDTO {

    private Long id;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String phone;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
