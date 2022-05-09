/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.Experience;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface ExperienceRepository extends
		PagingAndSortingRepository<Experience, Long> {

	void deleteByCv_Id(Long cvId);

	List<Experience> findAllByCv_Id(Long cvId);
	
	List<Experience> findByCompanyNameContaining(
			String companyName);
	
	List<Experience> findByRoleContaining(
			String companyName);
	
	List<Experience> findByExperienceTypeContaining(
			String companyName);
}
