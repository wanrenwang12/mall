package io.winters.mall.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {
    @NotBlank
    private String loginName;

    @NotBlank
    private String password;
}
