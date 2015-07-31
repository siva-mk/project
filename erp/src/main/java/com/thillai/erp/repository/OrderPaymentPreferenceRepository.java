package com.thillai.erp.repository;

import com.thillai.erp.domain.OrderPaymentPreference;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderPaymentPreference entity.
 */
public interface OrderPaymentPreferenceRepository extends JpaRepository<OrderPaymentPreference,Long> {

}
