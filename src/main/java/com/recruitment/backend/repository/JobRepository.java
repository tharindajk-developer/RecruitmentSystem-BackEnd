/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.Job;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

	Job findTopByOrderByIdDesc();

	List<Job> findAllByCategory(String category);
}
