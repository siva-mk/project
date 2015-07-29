package com.thillai.erp.repository;

import com.thillai.erp.domain.Ordertype;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ordertype entity.
 */
public interface OrdertypeRepository extends JpaRepository<Ordertype,Long> {

}
