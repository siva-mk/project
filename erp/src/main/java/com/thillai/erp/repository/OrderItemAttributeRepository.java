package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderItemAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderItemAttribute entity.
 */
public interface OrderItemAttributeRepository extends JpaRepository<OrderItemAttribute,Long> {

}
