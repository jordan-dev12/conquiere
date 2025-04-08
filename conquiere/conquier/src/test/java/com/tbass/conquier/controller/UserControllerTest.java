package com.tbass.conquier.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.tbass.conquier.AbstractIntegrationTest;
import com.tbass.conquier.dtos.PaginationDto;

@Transactional
public class UserControllerTest extends AbstractIntegrationTest {

	private static final String BASE_URL = "/api/user";

	@Nested
	@DisplayName("Find All")
	class findAll {

		@Test
		@WithMockUser(username = "user", roles = { "USER" })
		void findAll_Success_test() throws Exception {

			mockMvc
				.perform(post(BASE_URL + "/all").content(objectMapper.writeValueAsString(PaginationDto.builder().page(0).size(10).build()))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.users", hasSize((3))));

		}

	}

	@Nested
	@DisplayName("Get By ID")
	class GetByID {

		@Test
		@WithMockUser(username = "user", roles = { "USER" })
		void getById_Success_test() throws Exception {
			long userID = userHelper.users()
				.create()
				.name("Jean")
				.surname("Luc")
				.birthdate(LocalDate.of(1998, 01, 15))
				.email("j.luc@user.com")
				.save()
				.getId();

			mockMvc.perform(get(BASE_URL + "/get/{id}", userID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Jean")))
				.andExpect(jsonPath("$.surname", is("Luc")))
				.andExpect(jsonPath("$.email", is("j.luc@user.com")));

		}

	}

}
