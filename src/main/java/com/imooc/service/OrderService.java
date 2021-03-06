package com.imooc.service;

import com.imooc.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单接口
 */
public interface OrderService {
    /** 创建订单 */
    OrderDto createOrderMaster(OrderDto orderDto);
    /** 根据订单号查询订单 */
    OrderDto findOne(String orderId);
    /** 查询订单列表 */
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);
    /** 取消订单 */
    OrderDto cancel(OrderDto orderDto);
    /** 完结订单 */
    OrderDto finish(OrderDto orderDto);
    /** 支付订单 */
    OrderDto paid(OrderDto orderDto);
}
