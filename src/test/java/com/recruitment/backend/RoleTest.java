package com.recruitment.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import com.recruitment.backend.entity.Role;

public class RoleTest extends AbstractTest {

	Long roleId;

	String randomRole;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		randomRole = RandomStringUtils.random(10, true, false);
	}

	@Test
	public void testCreateRole() throws Exception {

		String uri = "/role/create";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders
						.post(uri)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(
								"{\"description\": \"recruiter description\",\"name\": \""
										+ randomRole + "\"}")).andReturn();

		if (!StringUtils.isEmpty(mvcResult.getResponse().getContentAsString())) {
			roleId = Long.valueOf(mvcResult.getResponse().getContentAsString()
					.split(",")[1].replaceAll("[^0-9]", ""));
		}

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void testGetRoles() throws Exception {
		String uri = "/role/all";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		List<Role> rolelist = super.mapFromJson(content, ArrayList.class);
		assertTrue(rolelist.size() > 0);
	}

	@Test
	public void testGetRole() throws Exception {

		if (!StringUtils.isEmpty(randomRole)) {
			testCreateRole();
		}
		String uri = "/role/getbyname?name=" + randomRole;
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Role role = super.mapFromJson(content, Role.class);
		assertTrue(role.getId() > 0);

		String deleteUri = "/role/delete?id=" + role.getId();
		MvcResult mvcResult2 = mvc.perform(
				MockMvcRequestBuilders.delete(deleteUri)).andReturn();
		int delStatus = mvcResult2.getResponse().getStatus();
		assertEquals(200, delStatus);
	}

}
