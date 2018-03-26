package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lee on 2018/1/9.
 */
@Data
@Entity
@DynamicUpdate
public class User {
    /** 主键ID */
    @Id
    private String id;
    /** 用户姓名 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 用户邮箱 */
    private String email;
    /** 用户电话 */
    private String phone;
    /** 密码找回问题 */
    private String question;
    /** 密码找回答案 */
    private String answer;
    /** 用户角色 */
    private Integer role;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
}
