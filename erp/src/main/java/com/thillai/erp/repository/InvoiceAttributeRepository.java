package com.thillai.erp.repository;

import com.thillai.erp.domain.InvoiceAttribute;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceAttribute entity.
 */
public interface InvoiceAttributeRepository extends JpaRepository<InvoiceAttribute,Long> {

}
