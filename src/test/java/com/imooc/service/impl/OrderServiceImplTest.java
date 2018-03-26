package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testCreateOrderMaster() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("YangDanjie");
        orderDto.setBuyerAddress("Tianjin");
        orderDto.setBuyerPhone("15945349780");
        orderDto.setBuyerOpenid("qw244021101");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderA = new OrderDetail();
        orderA.setProductId("dc4996bcb4404cc7a825791df8c8c0c6");
        orderA.setProductQuantity(1);
        OrderDetail orderB = new OrderDetail();
        orderB.setProductId("998e2b72e1484ab2b971896e98a2187b");
        orderB.setProductQuantity(1);
        orderDetailList.add(orderA);
        orderDetailList.add(orderB);
        orderDto.setOrderDetails(orderDetailList);
        OrderDto order = orderService.createOrderMaster(orderDto);
        Assert.assertNotNull(order);
        log.info("订单创建成功:{}" + order);
    }

    @Test
    public void testPaid() throws Exception {
        OrderDto orderDto = orderService.findOne("98956648674d4fa69414d840c39c27e9");
        OrderDto order = orderService.paid(orderDto);
        Assert.assertNotNull(order);
    }

    @Test
    public void testFindOne() throws Exception {
        OrderDto orderDto = orderService.findOne("1509377448117451854");
        System.out.println("orderDto:" + orderDto) ;
        Assert.assertNotNull(orderDto);
    }

    @Test
    public void testFindList() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDto> orderDtoPage = orderService.findList("qw244021102", pageRequest);
        Assert.assertNotNull(orderDtoPage);
    }

    @Test
    public void testCancel() throws Exception {
        OrderDto orderDto = orderService.findOne("1509377448117451854");
        OrderDto dto = orderService.cancel(orderDto);
        Assert.assertNotNull(dto);
    }

    @Test
    public void testFinish() throws Exception {
        OrderDto orderDto = orderService.findOne("1509377700428695330");
        OrderDto order = orderService.finish(orderDto);
        Assert.assertNotNull(order);
    }
}