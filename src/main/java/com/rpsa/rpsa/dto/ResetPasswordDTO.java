package com.rpsa.rpsa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResetPasswordDTO {
    private String username;
    private String currentpassword;
    private String newpassword;
    private String confirmpassword;
}
