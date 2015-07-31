package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderItemBilling;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderItemBilling entity.
 */
public interface OrderItemBillingRepository extends JpaRepository<OrderItemBilling,Long> {

}
