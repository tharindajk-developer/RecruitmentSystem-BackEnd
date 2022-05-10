/*
 * Recruitment System 2022
 */
package com.recruitment.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.recruitment.backend.entity.CV;
import com.recruitment.backend.service.CVService;
import com.recruitment.backend.util.ResponseModel;

/*
 *tharinda.jayamal@gmail.com
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cv")
public class CVController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${cv.path}")
	private String transactionPath;

	@Autowired
	private CVService cvService;

	@PostMapping("/create")
	public ResponseEntity<ResponseModel> createUpdateCV(
			HttpServletRequest httpServletRequestWrapper, @RequestBody CV cv,
			@RequestParam(value = "userId") Long userId) {

		log.debug("Post: /cv/create");

		try {
			log.debug("Adding accountType..");
			String status = cvService.addUpdateCV(cv, userId);
			log.debug(status + userId);
			ResponseModel responseModel = new ResponseModel(status + userId,
					"200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while adding the CV. Please try again."
					+ userId);
			ResponseModel responseModel = new ResponseModel(
					"An error occured while adding the CV. Please try again."
							+ userId, "500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseModel> deleteCV(
			HttpServletRequest httpServletRequestWrapper,
			@RequestParam(value = "userId", required = false) Long userId) {

		log.debug("Delete: /cv/delete");
		try {
			log.debug("Deleting a CV.." + userId);
			cvService.deleteCV(userId);
			log.debug("CV deleted succesfully " + userId);
			ResponseModel responseModel = new ResponseModel(
					"Succesfully deleted the CV", "200");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while deleting the CV. Please try again."
					+ userId);
			ResponseModel responseModel = new ResponseModel(
					"An error occured while deleting the CV. Please try again.",
					"500");
			return new ResponseEntity<ResponseModel>(responseModel,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getbyuserid")
	public CV getCVByUserId(HttpServletRequest request,
			@RequestParam(value = "userId", required = false) Long userId) {

		log.info("Get: /cv/getbyuserid");
		return cvService.getCVByUserId(userId);
	}
}