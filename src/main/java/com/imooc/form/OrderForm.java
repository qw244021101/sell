package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by win10 on 2017/11/23.
 */
@Data
public class OrderForm {
    /** 买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 买家手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /** 买家地址 */
    @NotEmpty(message = "地址必填")
    private String address;

    /** 买家openId */
    @NotEmpty(message = "openId必填")
    private String openId;

    /** 购物车信息 */
    @NotEmpty(message = "购物车信息不能为空")
    private String items;
}
