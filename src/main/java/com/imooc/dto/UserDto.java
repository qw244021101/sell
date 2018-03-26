package com.imooc.dto;

import lombok.Data;

/**
 * Created by lee on 2018/1/9.
 */
@Data
public class UserDto {
    /** 用户姓名 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 用户邮箱 */
    private String email;
    /** 用户电话 */
    private String phone;
}
