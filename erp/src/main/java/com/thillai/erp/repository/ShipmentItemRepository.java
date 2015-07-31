package com.thillai.erp.repository;

import com.thillai.erp.domain.ShipmentItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentItem entity.
 */
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem,Long> {

}
