/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recruitment.backend.entity.AppliedJobs;
import com.recruitment.backend.entity.Job;
import com.recruitment.backend.entity.User;
import com.recruitment.backend.repository.AppliedJobRepository;
import com.recruitment.backend.util.RequestModel;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class JobSeekerRecruiterService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Autowired
	private JobService jobService;

	@Autowired
	private AppliedJobRepository appliedJobRepository;

	public String contactApplicant(RequestModel requestModel) {

		String status = "Failed to send the email due to technical error.";
		User recruiter = userService.getUserById(requestModel
				.getRecruiterUserId());

		if (recruiter != null) {
			emailService.sendEmail(requestModel, recruiter);
			status = "Email sent successfully.";
		}

		return status;
	}

	public String hireJobSeeker(Long jobSeekerId, Long recruiterId) {

		String status = "Error occured while creating a hiring record";

		User jobSeeker = userService.getUserById(jobSeekerId);

		if (jobSeeker != null) {
			jobSeeker.setHiredBy(recruiterId);
			jobSeeker.setHired(true);
			jobSeeker.setHiredDate(new Date());
			
			userService.addUpdateUser(jobSeeker);

			status = "Successfully inserted hiring record.";
		}

		return status;
	}

	public String applyJob(Long jobSeekerId, Long jobId) {

		String status = "Error occured while creating a appliying the job.";

		User jobSeeker = userService.getUserById(jobSeekerId);
		Job job = jobService.getJobById(jobId);

		if (jobSeeker != null & job != null) {

			AppliedJobs appliedJobs = new AppliedJobs();
			appliedJobs.setJob(job);
			appliedJobs.setUser(jobSeeker);

			appliedJobRepository.save(appliedJobs);
			status = "Successfully applied for a job.";
		}

		return status;
	}

	public List<AppliedJobs> getAppliedJobs() {

		log.debug("Fetching all applied jobs.");

		Iterable<AppliedJobs> appliedJobs = appliedJobRepository.findAll();
		List<AppliedJobs> appliedJobsList = new ArrayList<>();
		for(AppliedJobs appJobs : appliedJobs){
			appJobs.setUser(userService.findByUserName(appJobs.getUser().getUserName()));
			appliedJobsList.add(appJobs);
		}

		return appliedJobsList;
	}

	public List<AppliedJobs> getAllAppliedJobsByUser(Long userId) {

		log.debug("Fetching applied jobs by user " + userId);
		User user = userService.getUserById(userId);
		List<AppliedJobs> appliedJobs = appliedJobRepository.findAllByUser(user);
		List<AppliedJobs> appliedJobsList = new ArrayList<>();
		for(AppliedJobs appJobs : appliedJobs){
			appJobs.setUser(userService.findByUserName(appJobs.getUser().getUserName()));
			appliedJobsList.add(appJobs);
		}

		return appliedJobsList;
	}
}
