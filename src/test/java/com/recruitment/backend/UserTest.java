package com.recruitment.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.recruitment.backend.entity.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends AbstractTest {

	Long userId = 0L;
	String randomTest;
	String userName;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		randomTest = generatingRandomStringUnbounded();
	}

	@Test
	public void testCreateUser() throws Exception {

		String uri = "/user/create";
		userName = "tharinda" + randomTest + "@gmail.com";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(
										"{\"firstName\": \"Tharinda\",\"hired\": false,\"hiredBy\": 0,\"hiredDate\": \"2022-05-07T09:24:47.317Z\",\"id\": 0,\"isActive\": true,\"lastName\": \"Kumarathunga\",\"password\": \"password\",\"role\": {  \"description\": \"job seeker\",  \"id\": 1,  \"name\": \"JOBSEEKER\"},\"userName\": \""
												+ userName + "\"}"))
				.andReturn();

		if (!StringUtils.isEmpty(mvcResult.getResponse().getContentAsString())) {
			userId = Long.valueOf(mvcResult.getResponse().getContentAsString()
					.split(",")[1].replaceAll("[^0-9]", ""));
		}

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void inactivateUser() throws Exception {
		if (userId == 0) {
			testCreateUser();
		}
		String uri = "/user/inactive?id=" + userId;
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	private String generatingRandomStringUnbounded() {
		return RandomStringUtils.random(4, true, true);
	}

	@Test
	public void testGetUser() throws Exception {

		if (StringUtils.isEmpty(userName)) {
			testCreateUser();
		}

		String uri = "/user/search/username";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"name\": \"" + userName + "\"}"))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertTrue(content.contains(userName));
	}

	@Test
	public void testGetUserByQualificationLevel() throws Exception {
		String uri = "/user/getbyqualificationlevel/3";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByMinQualificationLevel() throws Exception {
		String uri = "/user/getbyminqualificationlevel/1";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByMinGCSEPasses() throws Exception {
		String uri = "/user/getbymingcsepasses/1";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByAnySkillParam() throws Exception {
		String uri = "/user/getbyanyskillparameter?skill=software";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByAnyQualificationParam() throws Exception {
		String uri = "/user/getbyanyqualificationparameter?qualification=educational";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByAnyExperienceParam() throws Exception {
		String uri = "/user/getbyanyexperienceparameter?experience=full";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByJobSector() throws Exception {
		String uri = "/user/getbyjobsector?jobsector=IT";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@Test
	public void testGetUserByAnyQualificationTypeAndParam() throws Exception {
		String uri = "/user/getbyanyqualificationtypeandparameter?qualificationType=educational&searchParam=msc";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		List<User> userList = super.mapFromJson(mvcResult.getResponse()
				.getContentAsString(), ArrayList.class);
		assertTrue(userList.size() > 0);
	}

	@AfterTestMethod
	public void testChangePassword() throws Exception {

		String uri = "/user/changepassword";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(
										"{\"newPassword\": \"password1\",\"password\": \"password\",\"username\": \""+userName+"\"}"))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@AfterAll
	public void testDeleteUser() throws Exception {
		String uri = "/user/delete?id=" + userId;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
