package com.recruitment.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.recruitment.backend.entity.AppliedJobs;

public class JobSeekerRecruiterTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testContactApplicant() throws Exception {

		String uri = "/jobseekerrecruiter/contactapplicant";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(
										"{\"jobSeekerUserId\": 13,\"message\": \"Contact you for a new vacancy\",\"receiverEmail\": \"tharindajk@gmail.com\",\"recruiterUserId\": 18,\"subject\": \"New Vacancy2\"}"))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	//@Test
	public void testHireJobSeeker() throws Exception {

		String uri = "/jobseekerrecruiter/hireJobSeeker?jobSeekerId=232&recruiterId=18";

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	//@Test
	public void testApplyJob() throws Exception {

		String uri = "/jobseekerrecruiter/applyjob?jobSeekerId=13&jobId=22";

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void testGetAppliedJobs() throws Exception {
		String uri = "/jobseekerrecruiter/allapplied";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		List<AppliedJobs> appliedJobslist = super.mapFromJson(content,
				ArrayList.class);
		assertTrue(appliedJobslist.size() > 0);
	}

	@Test
	public void testGetAppliedJobsByUser() throws Exception {
		String uri = "/jobseekerrecruiter/getappliedjobsbyuser?userId=13";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
