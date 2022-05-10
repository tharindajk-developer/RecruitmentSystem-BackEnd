package com.recruitment.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.recruitment.backend.entity.Job;

public class JobTest extends AbstractTest {

	Long jobId;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testCreateJob() throws Exception {

		String uri = "/jobs/create";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(
										"{\"category\": \"Test3\",\"creationDate\": \"2022-05-07T15:33:46.402Z\",\"description\": \"Technician vacancies\",\"eligibility\": \"3+ years experience\",\"expiryDate\": \"2022-05-14T15:33:46.402Z\",\"name\": \"Technician\"}"))
				.andReturn();

		if (!StringUtils.isEmpty(mvcResult.getResponse().getContentAsString())) {
			jobId = Long.valueOf(mvcResult.getResponse().getContentAsString()
					.split(",")[1].replaceAll("[^0-9]", ""));
		}

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String deleteUri = "/jobs/delete?id=" + jobId;
		MvcResult mvcResult2 = mvc.perform(
				MockMvcRequestBuilders.delete(deleteUri)).andReturn();
		int delStatus = mvcResult2.getResponse().getStatus();
		assertEquals(200, delStatus);
	}

	@Test
	public void testGetJobList() throws Exception {
		String uri = "/jobs/all";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Job[] joblist = super.mapFromJson(content, Job[].class);
		assertTrue(joblist.length > 0);
	}

	@Test
	public void testGetJob() throws Exception {
		String uri = "/jobs/getbycategory?category=IT";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Job[] joblist = super.mapFromJson(content, Job[].class);
		assertTrue(joblist.length > 0);
	}

}
