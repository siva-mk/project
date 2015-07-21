package com.thillai.erp.repository;

import com.thillai.erp.domain.Productcategory;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Productcategory entity.
 */
public interface ProductcategoryRepository extends JpaRepository<Productcategory,Long> {

}
