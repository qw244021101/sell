package com.imooc.dataobject;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2018/1/18.
 */
@Data
public class Role {
    /** 角色ID */
    private Integer rid;
    /** 角色名称 */
    private String name;
    /** 角色对应权限集合 */
    private Set<Permission> permissions = new HashSet<>();
}
