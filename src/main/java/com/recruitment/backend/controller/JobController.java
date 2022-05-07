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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.backend.entity.Job;
import com.recruitment.backend.service.JobService;
import com.recruitment.backend.util.ResponseModel;

/*
 *tharinda.jayamal@gmail.com
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/jobs")
public class JobController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobService jobService;

	@PostMapping("/create")
	public ResponseEntity<ResponseModel> createJob(
			HttpServletRequest httpServletRequestWrapper, @RequestBody Job job) {

		log.debug("Post: /job/create");

		try {
			log.debug("Adding job..");
			job = jobService.addUpdateJob(job);
			log.debug("Job added succesfully " + job.getId());
			ResponseModel responseModel = new ResponseModel(
					"Succesfully added the job " + job.getId(), "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while adding the job. Please try again."
					+ job.getId());
			ResponseModel responseModel = new ResponseModel(
					"An error occured while adding the job. Please try again."
							+ job.getId(), "500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/search/{page}")
	public Page<Job> searchJobs(
			HttpServletRequestWrapper httpServletRequestWrapper,
			@PathVariable(value = "page") int page) {

		log.info("Post: /job/search");
		return jobService.getAllJobs(page);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseModel> deleteJob(
			HttpServletRequest httpServletRequestWrapper,
			@RequestParam(value = "id", required = false) Long id) {

		log.debug("Delete: /job/delete");
		try {
			log.debug("Deleting job.." + id);
			jobService.deleteJob(id);
			log.debug("Job deleted succesfully " + id);
			ResponseModel responseModel = new ResponseModel(
					"Succesfully deleted the job", "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while deleting the job. Please try again."
					+ id);
			ResponseModel responseModel = new ResponseModel(
					"An error occured while deleting the job. Please try again.",
					"500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public List<Job> getJobs(HttpServletRequest request) {

		log.info("Get: /job/all");
		return jobService.getAllJobs();
	}

	@GetMapping("/getbycategory")
	public List<Job> getJob(HttpServletRequest request,
			@RequestParam(value = "category", required = false) String category) {

		log.info("Get: /job/getbyname");
		return jobService.getJobByCategory(category);
	}
}