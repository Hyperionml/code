package com.hyperionml.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String name;
    private String userName;
    private String password;
    private String gender;
    private String root;
}
