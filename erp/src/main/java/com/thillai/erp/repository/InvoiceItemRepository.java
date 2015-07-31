package com.thillai.erp.repository;

import com.thillai.erp.domain.InvoiceItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InvoiceItem entity.
 */
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {

}
