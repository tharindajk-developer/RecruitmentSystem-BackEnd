/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.CV;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface CVRepository extends PagingAndSortingRepository<CV, Long> {

	CV findTopByOrderByIdDesc();
	
	List<CV> findAllByNoOfGCSEpassesGreaterThanEqual(Integer noOfGCSEpasses);
}
