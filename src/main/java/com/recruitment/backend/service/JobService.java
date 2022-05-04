/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.recruitment.backend.entity.Job;
import com.recruitment.backend.repository.JobRepository;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class JobService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobRepository jobRepository;

	public Job addUpdateJob(Job job) {

		return jobRepository.save(job);
	}

	public Page<Job> getAllJobs(int page) {

		log.debug("Fetching jobs.");
		return jobRepository.findAll(PageRequest.of(page, 12));
	}

	public Job getJobById(Long id) {

		log.debug("Fetching job for :" + id);
		if (jobRepository.findById(id).isPresent()) {
			return jobRepository.findById(id).get();
		} else {
			return null;
		}
	}

	public void deleteJob(Long id) {

		log.debug("Deleting job : " + id);
		jobRepository.deleteById(id);
	}

	public List<Job> getAllJobs() {

		log.debug("Fetching all jobs.");

		Iterable<Job> jobs = jobRepository.findAll();

		return StreamSupport.stream(jobs.spliterator(), false).collect(
				Collectors.toList());
	}

	public List<Job> getJobByCategory(String category) {

		log.debug("Fetching category " + category);
		return jobRepository.findAllByCategory(category);
	}

}
