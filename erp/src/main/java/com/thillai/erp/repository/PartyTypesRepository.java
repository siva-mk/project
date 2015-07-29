package com.thillai.erp.repository;

import com.thillai.erp.domain.PartyTypes;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PartyTypes entity.
 */
public interface PartyTypesRepository extends JpaRepository<PartyTypes,Long> {

}
