package com.thillai.erp.repository;

import com.thillai.erp.domain.InventoryItemStatus;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventoryItemStatus entity.
 */
public interface InventoryItemStatusRepository extends JpaRepository<InventoryItemStatus,Long> {

}
