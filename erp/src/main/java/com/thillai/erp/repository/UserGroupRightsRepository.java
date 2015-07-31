package com.thillai.erp.repository;

import com.thillai.erp.domain.UserGroupRights;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserGroupRights entity.
 */
public interface UserGroupRightsRepository extends JpaRepository<UserGroupRights,Long> {

}
