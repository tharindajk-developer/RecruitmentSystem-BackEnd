/*
 * Recruitment System 2022
 */
package com.recruitment.backend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.backend.entity.AppliedJobs;
import com.recruitment.backend.service.JobSeekerRecruiterService;
import com.recruitment.backend.util.RequestModel;
import com.recruitment.backend.util.ResponseModel;

/*
 *tharinda.jayamal@gmail.com
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/jobseekerrecruiter")
public class JobSeekerRecruiterController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobSeekerRecruiterService jobSeekerRecruiterService;

	@PostMapping("/contactapplicant")
	public ResponseEntity<ResponseModel> contactApplicant(
			HttpServletRequest httpServletRequestWrapper,
			@RequestBody RequestModel requestModel) {

		log.debug("Post: /jobseekerrecruiter/contactapplicant");

		try {
			log.debug("Contact Applicant..");
			String status = jobSeekerRecruiterService
					.contactApplicant(requestModel);
			log.debug(status);
			ResponseModel responseModel = new ResponseModel(
					"Succesfully sent the email."
							+ requestModel.getReceiverEmail(), "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while sending the email. Please try again."
					+ requestModel.getReceiverEmail());
			ResponseModel responseModel = new ResponseModel(
					"An error occured while sending the email. Please try again."
							+ requestModel.getReceiverEmail(), "500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/hireJobSeeker")
	public ResponseEntity<ResponseModel> hireJobSeeker(
			HttpServletRequestWrapper httpServletRequestWrapper,
			@RequestParam(value = "jobSeekerId") Long jobSeekerId,
			@RequestParam(value = "recruiterId") Long recruiterId) {

		log.debug("Post: /jobseekerrecruiter/hireJobSeeker");

		try {
			log.debug("Hire Job Seeker..");
			String status = jobSeekerRecruiterService.hireJobSeeker(
					jobSeekerId, recruiterId);
			log.debug(status);
			ResponseModel responseModel = new ResponseModel(status, "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while sending the email. Please try again. Job Seeker : "
					+ jobSeekerId + " Recruiter : " + recruiterId);
			ResponseModel responseModel = new ResponseModel(
					"An error occured while sending the email. Please try again. Job Seeker : "
							+ jobSeekerId + " Recruiter : " + recruiterId,
					"500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/applyjob")
	public ResponseEntity<ResponseModel> applyJob(
			HttpServletRequestWrapper httpServletRequestWrapper,
			@RequestParam(value = "jobSeekerId") Long jobSeekerId,
			@RequestParam(value = "jobId") Long jobId) {

		log.debug("Post: /jobseekerrecruiter/applyjob");

		try {
			log.debug("Applying a Job..");
			String status = jobSeekerRecruiterService.applyJob(jobSeekerId,
					jobId);
			log.debug(status);
			ResponseModel responseModel = new ResponseModel(status, "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while applying the job. Please try again. Job Seeker : "
					+ jobSeekerId + " Job Id : " + jobId);
			ResponseModel responseModel = new ResponseModel(
					"An error occured while applying the job. Please try again. Job Seeker : "
							+ jobSeekerId + " Job Id : " + jobId, "500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/allapplied")
	public List<AppliedJobs> getAppliedJobs(HttpServletRequest request) {

		log.info("Get: /jobseekerrecruiter/allapplied");
		return jobSeekerRecruiterService.getAppliedJobs();
	}

	@GetMapping("/getappliedjobsbyuser")
	public List<AppliedJobs> getAppliedJobsByUser(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) {

		log.info("Get: /role/getappliedjobsbyuser");
		return jobSeekerRecruiterService.getAllAppliedJobsByUser(userId);
	}
}
