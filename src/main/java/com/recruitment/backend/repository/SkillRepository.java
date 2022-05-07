/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.Skill;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface SkillRepository extends PagingAndSortingRepository<Skill, Long>{

	void deleteByCv_Id(Long cvId);
	
	List<Skill> findAllByCv_Id(Long cvId);
}
