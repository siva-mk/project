package com.thillai.erp.repository;

import com.thillai.erp.domain.UserRights;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserRights entity.
 */
public interface UserRightsRepository extends JpaRepository<UserRights,Long> {

}
