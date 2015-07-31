package com.thillai.erp.repository;

import com.thillai.erp.domain.InvoiceType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceType entity.
 */
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType,Long> {

}
