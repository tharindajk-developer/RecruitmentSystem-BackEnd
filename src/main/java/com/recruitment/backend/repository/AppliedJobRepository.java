/*
 * Recruitment System 2022
 */
package com.recruitment.backend.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.recruitment.backend.entity.AppliedJobs;
import com.recruitment.backend.entity.Job;
import com.recruitment.backend.entity.User;

/*
 *tharinda.jayamal@gmail.com
 */
@Repository
public interface AppliedJobRepository extends
		PagingAndSortingRepository<AppliedJobs, Long> {

	Job findTopByOrderByIdDesc();

	List<AppliedJobs> findAllByUser(User user);
}
