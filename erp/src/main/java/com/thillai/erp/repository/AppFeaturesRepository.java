package com.thillai.erp.repository;

import com.thillai.erp.domain.AppFeatures;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AppFeatures entity.
 */
public interface AppFeaturesRepository extends JpaRepository<AppFeatures,Long> {

}
