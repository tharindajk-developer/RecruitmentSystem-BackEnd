/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.Role;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

	Role findTopByOrderByIdDesc();

	Role findByName(String name);
}
