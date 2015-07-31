package com.thillai.erp.repository;

import com.thillai.erp.domain.InventoryItemAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InventoryItemAttribute entity.
 */
public interface InventoryItemAttributeRepository extends JpaRepository<InventoryItemAttribute,Long> {

}
