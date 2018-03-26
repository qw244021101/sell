package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testFindByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0,15);
        Page page = orderMasterRepository.findByBuyerOpenid("qw244021101", pageRequest);
        System.out.println("page:" + page);
    }

    @Test
    public void testSave(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(UUID.randomUUID().toString().replace("-",""));
        orderMaster.setBuyerOpenid(UUID.randomUUID().toString().replace("-",""));
        orderMaster.setBuyerName("Lee");
        orderMaster.setBuyerPhone("15945349780");
        orderMaster.setBuyerAddress("Shanghai");
        orderMaster.setBuyerOpenid("15945349780");
        orderMaster.setOrderAmount(new BigDecimal("1099"));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCoed());
        OrderMaster order = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(order);
    }
}