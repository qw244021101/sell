package com.imooc.dataobject;

import lombok.Data;

/**
 * Created by lee on 2018/1/18.
 */
@Data
public class Permission {
    /** 权限ID */
    private Integer pid;
    /** 权限名称 */
    private String name;
    /** 权限路径 */
    private String url;
}
