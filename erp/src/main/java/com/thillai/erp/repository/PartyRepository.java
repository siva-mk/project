package com.thillai.erp.repository;

import com.thillai.erp.domain.Party;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Party entity.
 */
public interface PartyRepository extends JpaRepository<Party,Long> {

}
