package com.imooc.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.viewobject.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by win10 on 2017/11/22.
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping(value = "/create")
    public ResultVo createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【订单参数不正确】,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto = new OrderDto();
        // datatransfer
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenId());
        // json => list
        Gson gson = new Gson();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误，string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (orderDetails.isEmpty()) {
            log.error("购物车不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetails(orderDetails);
        OrderDto dto = orderService.createOrderMaster(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", dto.getOrderId());
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), map);
    }
    // 查询订单列表
    @PostMapping(value = "/list")
    public ResultVo findOrderList(@RequestParam("openId") String openId,
                                  @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
        if (StringUtils.isEmpty(openId)) {
            log.error("用户openId不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        Page page = orderService.findList(openId, pageRequest);
        List<OrderDto> list = page.getContent();
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), list);
    }
    // 订单详情
    @PostMapping(value = "/detail")
    public ResultVo orderDetail(@RequestParam("openId") String openId, @RequestParam("orderId") String orderId) {
        // TODO
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)){
            log.error("openId or orderId is null");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDto orderDto = buyerService.findOrder(openId, orderId);
        Map<String, OrderDto> map = new HashMap<>();
        map.put("order", orderDto);
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), map);
    }
    // 取消订单
    @PostMapping(value = "/cancel")
    public ResultVo cancelOrder(@RequestParam("openId") String openId, @RequestParam("orderId") String orderId) {
        // TODO
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)){
            log.error("openId or orderId is null");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDto orderDto = buyerService.findOrder(openId, orderId);
        orderService.cancel(orderDto);
        return new ResultVo(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }
}
