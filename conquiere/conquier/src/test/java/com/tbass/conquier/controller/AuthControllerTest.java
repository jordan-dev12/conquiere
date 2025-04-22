package com.tbass.conquier.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.tbass.conquier.AbstractIntegrationTest;
import com.tbass.conquier.dtos.AuthRequestDto;

@Transactional
public class AuthControllerTest extends AbstractIntegrationTest {

	private final String BASE_URL = "/api/auth";

	@BeforeEach
	protected void initUser() {
		userHelper.defaultDataSet();
	}

	@Nested
	@DisplayName("Authentification")
	class Authentification {

		@Test
		void ok() throws Exception {

			AuthRequestDto authRequest = new AuthRequestDto("Jean@user.com", "password123");

			mockMvc
				.perform(post(BASE_URL + "/login").content(objectMapper.writeValueAsString(authRequest))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jwtToken").isNotEmpty());

		}

		@Test
		void passwordInvalid() throws Exception {

			AuthRequestDto authRequest = new AuthRequestDto("Jean@user.com", "password123adas");

			mockMvc
				.perform(post(BASE_URL + "/login").content(objectMapper.writeValueAsString(authRequest))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.detail", equalTo("INVALID_CREDENTIALS")));

		}

		@Test
		void userInvalid() throws Exception {

			userHelper.users().deleteAll();
			AuthRequestDto authRequest = new AuthRequestDto("Jean@user.com", "password123");

			mockMvc
				.perform(post(BASE_URL + "/login").content(objectMapper.writeValueAsString(authRequest))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.detail", equalTo("INVALID_CREDENTIALS")));

		}

	}

}
