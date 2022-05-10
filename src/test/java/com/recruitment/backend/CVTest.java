package com.recruitment.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.recruitment.backend.entity.CV;

public class CVTest extends AbstractTest {

	Long userId;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		userId = 14L;
	}

	@Test
	public void createUpdateCV() throws Exception {

		String uri = "/cv/create?userId=14";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(
										"{ \"address\": {\"addressLine1\": \"No 02\",\"addressLine2\": \"Peradeniya Road\",\"city\": \"Kandy\",\"state\": \"Central\"},\"contactNo\": \"0716062443\",  \"creationDate\": \"2022-05-07T15:43:04.772Z\",\"experiences\": [{\"companyName\": \"HCL\",\"experienceType\": \"Full time\",\"role\": \"Tech Lead\",\"startDate\": \"2021-06-30T15:43:04.772Z\"}],\"jobSector\": \"IT\",\"noOfGCSEpasses\": 3,\"qualifications\": [{\"qualificationDate\": \"2022-05-05T15:43:04.772Z\",  \"qualificationLevel\": 3,\"qualificationName\": \"MSc\",\"qualificationType\": \"PostGrad\"}],\"skills\": [{\"skillName\": \"Testing\",\"skillType\": \"Software\"}]}"))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void getCVByUserId() throws Exception {
		String uri = "/cv/getbyuserid?userId=" + userId;
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		CV cv = super.mapFromJson(content, CV.class);
		assertTrue(cv.getId() > 0);

		String deleteUri = "/cv/delete?userId=" + userId;
		MvcResult mvcResult2 = mvc.perform(
				MockMvcRequestBuilders.delete(deleteUri)).andReturn();
		int delStatus = mvcResult2.getResponse().getStatus();
		assertEquals(200, delStatus);
	}
}
