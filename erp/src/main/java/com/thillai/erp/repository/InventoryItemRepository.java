package com.thillai.erp.repository;

import com.thillai.erp.domain.InventoryItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventoryItem entity.
 */
public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {

}
