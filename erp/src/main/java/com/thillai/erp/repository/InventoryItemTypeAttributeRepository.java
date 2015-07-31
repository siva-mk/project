package com.thillai.erp.repository;

import com.thillai.erp.domain.InventoryItemTypeAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventoryItemTypeAttribute entity.
 */
public interface InventoryItemTypeAttributeRepository extends JpaRepository<InventoryItemTypeAttribute,Long> {

}
