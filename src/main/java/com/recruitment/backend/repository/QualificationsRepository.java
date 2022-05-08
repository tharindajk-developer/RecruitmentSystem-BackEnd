/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.Qualification;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface QualificationsRepository extends
		PagingAndSortingRepository<Qualification, Long> {

	void deleteByCv_Id(Long cvId);

	List<Qualification> findAllByCv_Id(Long cvId);

	List<Qualification> findAllByQualificationLevel(Integer level);

	List<Qualification> findAllByQualificationLevelGreaterThanEqual(
			Integer level);

	List<Qualification> findByQualificationTypeContaining(
			String qualificationType);

	List<Qualification> findByQualificationNameContaining(
			String qualificationName);
}
