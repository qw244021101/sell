package com.imooc.service.impl;

import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderExceptionEnum;
import com.imooc.exception.SellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by win10 on 2017/11/25.
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    public OrderDto findOrder(String openId, String orderId) {
        return this.checkOrder(openId, orderId);
    }

    public OrderDto cancelOrder(String orderId, String openId) {
        OrderDto orderDto = this.checkOrder(openId, orderId);
        if (orderDto == null) {
            log.error("【取消订单】订单不存在，orderId={}", orderId);
            throw new SellException(OrderExceptionEnum.ORDER_NOT_EXISTS);
        }
        orderService.cancel(orderDto);
        return orderDto;
    }

    private OrderDto checkOrder(String openId, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            return null;
        }
        if (!orderDto.getBuyerOpenid().equalsIgnoreCase(openId)) {
            log.error("【查询订单】订单的openId不一致, openId={}", openId);
            throw new SellException(OrderExceptionEnum.ORDER_OPENID_ERROR);
        }
        return orderDto;
    }
}
