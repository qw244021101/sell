package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String >{
    /**
     * 根据用户微信号名查询
     */
    Page<OrderMaster> findByBuyerOpenid(String openId, Pageable pageable);
}
