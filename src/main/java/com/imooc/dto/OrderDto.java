package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.Data;
import util.serializer.DateLongSerializer;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    /** 订单ID */
    private String orderId;
    /** 买家姓名 */
    private String buyerName;
    /** 买家电话 */
    private String buyerPhone;
    /** 买家地址 */
    private String buyerAddress;
    /** 买家微信号 */
    private String buyerOpenid;
    /** 买家付款状态 */
    private Integer payStatus = PayStatusEnum.WAIT.getCoed();
    /** 买家订单状态 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 订单创建时间 */
    @JsonSerialize(using = DateLongSerializer.class)
    private Date createTime;
    /** 订单更新时间 */
    @JsonSerialize(using = DateLongSerializer.class)
    private Date updateTime;
    /** 订单详情列表 */
    List<OrderDetail> orderDetails;
    /** 订单总金额 */
    private BigDecimal orderAmount;
}
