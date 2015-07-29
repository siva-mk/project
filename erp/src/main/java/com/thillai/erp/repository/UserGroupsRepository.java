package com.thillai.erp.repository;

import com.thillai.erp.domain.UserGroups;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserGroups entity.
 */
public interface UserGroupsRepository extends JpaRepository<UserGroups,Long> {

}
