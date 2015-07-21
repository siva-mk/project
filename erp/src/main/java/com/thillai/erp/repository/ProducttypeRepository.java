package com.thillai.erp.repository;

import com.thillai.erp.domain.Producttype;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Producttype entity.
 */
public interface ProducttypeRepository extends JpaRepository<Producttype,Long> {

}
