package com.thillai.erp.repository;

import com.thillai.erp.domain.UserGroupMembership;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserGroupMembership entity.
 */
public interface UserGroupMembershipRepository extends JpaRepository<UserGroupMembership,Long> {

}
