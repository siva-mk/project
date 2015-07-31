package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderTypeAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderTypeAttribute entity.
 */
public interface OrderTypeAttributeRepository extends JpaRepository<OrderTypeAttribute,Long> {

}
