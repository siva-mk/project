package com.thillai.erp.repository;

import com.thillai.erp.domain.Shipment;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shipment entity.
 */
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

}
