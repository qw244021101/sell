package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDto;
import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderExceptionEnum;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ProductExceptionEnum;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import util.KeyUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单接口实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public OrderDto createOrderMaster(OrderDto orderDto) {
        String orderId = KeyUtil.getUniqueKey();
        // 初始化总价
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        // 1:查询商品(商品价格、数量)
        for (OrderDetail order:orderDto.getOrderDetails()) {
            ProductInfo productInfo = productInfoRepository.findOne(order.getProductId());
            if (productInfo == null) {
                throw new SellException(ProductExceptionEnum.PRODUCT_NOT_EXISTS);
            }
            // 2:计算总价
            totalPrice = productInfo.getProductPrice().multiply(new BigDecimal(order.getProductQuantity())).add(totalPrice);
            // 3:写入订单详情表
            order.setOrderId(orderId);
            order.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo, order);
            orderDetailRepository.save(order);
        }
        // 4:扣库存
        List<CartDto> cartDtoList = orderDto.getOrderDetails().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);
        // 5:写入订单主表
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        //
        orderDto.setOrderAmount(totalPrice);
        orderDto.setOrderId(orderId);
        //
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMasterRepository.save(orderMaster);
        return orderDto;
    }

    public OrderDto paid(OrderDto orderDto) {
        // 判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单失败，orderDto={}",orderDto);
            throw new SellException(OrderExceptionEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCoed())){
            log.error("【支付订单】订单失败， orderDto={}", orderDto);
            throw new SellException(OrderExceptionEnum.ORDER_PAY_FAILED);
        }
        // 修改订单状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCoed());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster order = orderMasterRepository.save(orderMaster);
        if (order == null){
            log.error("【完结订单】订单失败，orderMaster={}", orderMaster);
            throw new SellException(OrderExceptionEnum.ORDER_UPDATE_FAILED);
        }
        return orderDto;
    }

    /**
     * 查询订单
     * @param orderId
     * @return
     */
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(OrderExceptionEnum.ORDER_NOT_EXISTS);
        }
        // 查询订单详情
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if(orderDetails.size() == 0){
            throw new SellException(OrderExceptionEnum.ORDER_DETAIL_NOT_EXISTS);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetails(orderDetails);
        return orderDto;
    }

    /**
     *  查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDto> orderDtoList = orderMasterPage.getContent().stream().map(e -> this.convert(e)).collect(Collectors.toList());
        Page page = new PageImpl< >(orderDtoList, pageable, orderMasterPage.getTotalElements());
        return page;
    }

    /**
     *  取消订单
     * @param orderDto
     * @return
     */
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getPayStatus());
            throw new SellException(OrderExceptionEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if (master == null){
            log.error("【取消订单】订单失败，orderMaster={}", orderMaster);
            throw new SellException(OrderExceptionEnum.ORDER_UPDATE_FAILED);
        }
        // 返还库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetails())){
            log.error("【取消订单】订单失败,orderMaster={}", orderMaster);
            throw new SellException(OrderExceptionEnum.ORDER_DETAILS_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetails().stream().map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDtoList);
        // 如果客户已支付，需要退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCoed())){
            //TODO
        }
        return orderDto;
    }

    public OrderDto finish(OrderDto orderDto) {
        // 判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单失败，order={}",orderDto);
            throw new SellException(OrderExceptionEnum.ORDER_STATUS_ERROR);
        }
        orderDto.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster order = orderMasterRepository.save(orderMaster);
        if (order == null){
            log.error("【完结订单】订单失败，orderMaster={}", orderMaster);
            throw new SellException(OrderExceptionEnum.ORDER_UPDATE_FAILED);
        }
        return orderDto;
    }

    /**
     * 将orderMaster转化为orderDto
     * @param orderMaster
     * @return
     */
    private OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return orderDto;
    }
}
