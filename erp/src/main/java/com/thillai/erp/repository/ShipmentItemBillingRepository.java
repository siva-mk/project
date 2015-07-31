package com.thillai.erp.repository;

import com.thillai.erp.domain.ShipmentItemBilling;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShipmentItemBilling entity.
 */
public interface ShipmentItemBillingRepository extends JpaRepository<ShipmentItemBilling,Long> {

}
