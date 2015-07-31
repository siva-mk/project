package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderStatus;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderStatus entity.
 */
public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {

}
