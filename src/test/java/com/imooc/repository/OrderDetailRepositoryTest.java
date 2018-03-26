package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 *
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testFindByOrderId() throws Exception {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId("98956648674d4fa69414d840c39c27e9");
        Assert.assertNotEquals(0, orderDetails.size());
    }

    @Test
    public void testSave(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(UUID.randomUUID().toString().replace("-",""));
        orderDetail.setOrderId("98956648674d4fa69414d840c39c27e9");
        orderDetail.setProductId("49bda9965da3407792358bba8c9d4dc8");
        orderDetail.setProductName("Lego21030");
        orderDetail.setProductPrice(new BigDecimal("1099"));
        orderDetail.setProductQuantity(23);
        orderDetail.setProductIcon("www.lego.com");
        OrderDetail order = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(order);
    }
}