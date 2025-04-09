package com.tbass.conquier.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.tbass.conquier.AbstractIntegrationTest;
import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserRegistrationRequestDto;

@Transactional
public class UserControllerTest extends AbstractIntegrationTest {

	private static final String BASE_URL = "/api/user";

	@BeforeEach
	protected void initUser() {
		userHelper.defaultDataSet();
	}

	@Nested
	@DisplayName("Find User")
	class findAll {

		@Test
		@WithMockUser(username = "admin", roles = { "ADMIN" })
		void usersFound() throws Exception {

			mockMvc
				.perform(post(BASE_URL + "/all").content(objectMapper.writeValueAsString(PaginationDto.builder().page(0).size(10).build()))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.users", hasSize((2))));

		}

		@Test
		@WithMockUser(username = "admin", roles = { "ADMIN" })
		void noUser() throws Exception {

			userHelper.users().deleteAll();
			mockMvc.perform(post(BASE_URL + "/all").content(objectMapper.writeValueAsString(PaginationDto.builder().page(0).size(10).build()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.users", hasSize((0))));

		}

	}

	@Nested
	@DisplayName("Get User By ID")
	class GetByID {

		@Test
		@WithMockUser(username = "user", roles = { "USER" })
		void found() throws Exception {
			long userID = idHelper.getUserIdByEmail("Jean@user.com");

			mockMvc.perform(get(BASE_URL + "/get/{id}", userID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Jean")))
				.andExpect(jsonPath("$.surname", is("Jean")))
				.andExpect(jsonPath("$.email", is("Jean@user.com")));

		}

		@Test
		@WithMockUser(username = "user", roles = { "USER" })
		void noFound() throws Exception {

			mockMvc.perform(get(BASE_URL + "/get/{id}", 15))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.detail", equalTo("Utilisateur non trouvé avec l'ID: 15")));

		}

	}

	@Nested
	@DisplayName("Create User")
	class CreateUser {

		@Test
		void createUserOk() throws Exception {
			UserRegistrationRequestDto userRequestDto = UserRegistrationRequestDto.builder()
				.email("test1@user.com")
				.name("test1")
				.surname("test1")
				.password("password123")
				.birthdate(LocalDate.of(2000, 06, 15))
				.build();

			mockMvc.perform(post(BASE_URL + "/register").content(objectMapper.writeValueAsString(userRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email", is("test1@user.com")))
				.andExpect(jsonPath("$.surname", is("test1")))
				.andExpect(jsonPath("$.name", is("test1")));

		}

	}

	@Nested
	@DisplayName("Delete User")
	class DeleteUser {

		@Test
		@WithMockUser(username = "user", roles = { "ADMIN" })
		void deleteUserOk() throws Exception {
			long userID = idHelper.getUserIdByEmail("Jean@user.com");

			mockMvc.perform(delete(BASE_URL + "/delete/{id}", userID))
				.andExpect(status().isNoContent());

		}

		@Test
		@WithMockUser(username = "admin", roles = { "ADMIN" })
		void noUserDelete() throws Exception {

			mockMvc.perform(delete(BASE_URL + "/delete/20"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.detail", equalTo("Utilisateur non trouvé avec l'ID: 20")));

		}

	}

}
