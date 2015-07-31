package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderAttribute entity.
 */
public interface OrderAttributeRepository extends JpaRepository<OrderAttribute,Long> {

}
