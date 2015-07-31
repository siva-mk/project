package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderHeader;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderHeader entity.
 */
public interface OrderHeaderRepository extends JpaRepository<OrderHeader,Long> {

}
