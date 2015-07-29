package com.thillai.erp.repository;

import com.thillai.erp.domain.InventoryItemType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventoryItemType entity.
 */
public interface InventoryItemTypeRepository extends JpaRepository<InventoryItemType,Long> {

}
