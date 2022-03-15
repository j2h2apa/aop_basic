package com.cos.aopdemo.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateReqDto {
    @NotNull(message = "password가 입력되지 않았습니다.")
    @NotBlank(message = "password가 입력되지 않았습니다.")
    private String password;
    private String phone;
}
