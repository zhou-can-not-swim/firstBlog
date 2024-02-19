package com.zqy.demo_java.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "昵称不能为空")
    private String password;
}
