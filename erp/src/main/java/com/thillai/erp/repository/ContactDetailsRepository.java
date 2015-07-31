package com.thillai.erp.repository;

import com.thillai.erp.domain.ContactDetails;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContactDetails entity.
 */
public interface ContactDetailsRepository extends JpaRepository<ContactDetails,Long> {

}
