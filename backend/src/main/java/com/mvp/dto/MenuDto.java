package com.mvp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MenuDto {

    private int id;
    private String menuName;
    private String menuUrl;
    private LocalDate createTime;
}
