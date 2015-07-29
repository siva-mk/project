package com.thillai.erp.repository;

import com.thillai.erp.domain.ShipmentType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentType entity.
 */
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType,Long> {

}
