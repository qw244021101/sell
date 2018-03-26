package com.imooc.service;

import com.imooc.dto.OrderDto;

/**
 * Created by win10 on 2017/11/25.
 */
public interface BuyerService {
    // 查询订单
    OrderDto findOrder(String openId, String orderId);
    // 取消订单
    OrderDto cancelOrder(String openId, String orderId);
}
