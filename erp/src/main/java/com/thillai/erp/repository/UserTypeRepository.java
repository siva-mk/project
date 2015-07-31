package com.thillai.erp.repository;

import com.thillai.erp.domain.UserType;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserType entity.
 */
public interface UserTypeRepository extends JpaRepository<UserType,Long> {

}
