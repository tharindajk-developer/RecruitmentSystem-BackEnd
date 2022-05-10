package com.recruitment.backend;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

public class JwtAuthenticationTest extends AbstractTest{

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testCreateAuthenticationToken() throws Exception {

		String uri = "/authenticate";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content("{\"username\": \"tharindajk@gmail.com\",\"password\": \"password3\"}"))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test(expected=Exception.class)
	public void testCreateAuthenticationTokenInvalid() throws Exception {

		String uri = "/authenticate";
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders
								.post(uri)
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content("{\"username\": \"tharindajk@gmail.com\",\"password\": \"password1\"}"))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
