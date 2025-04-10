package com.tbass.conquier.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.tbass.conquier.AbstractIntegrationTest;

@Transactional
public class RegistrationOfTournamentControllerTest extends AbstractIntegrationTest {

	private final String BASE_URL = "/api/regis";

	@BeforeEach
	public void initRegistration() {

		userHelper.users().deleteAll();
		tournamentHelper.defaultDataSet();
		userHelper.users().create().regularUser("Mario");
	}

	@Nested
	@DisplayName("Registration User of Tournoi")
	@WithMockUser(username = "Mario@user.com", roles = { "USER" })
	class Registration {

		@Test
		void registerOk() throws Exception {
			Long tournamentId = idHelper.getTournamentIdByName("Calcio");

			mockMvc.perform(post(BASE_URL + "/register/{id}", tournamentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", equalTo("Utilisateur bien inscrit au tournoi")));

		}

		@Test
		void noFoundTournoi() throws Exception {

			mockMvc.perform(post(BASE_URL + "/register/{id}", 60))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.detail", equalTo("Tournoi non trouvé avec l'ID: 60")));

		}

	}

	@Nested
	@DisplayName("Unregistration User of Tournoi")
	@WithMockUser(username = "Mario@user.com", roles = { "USER" })
	class UnRegistration {

		@Test
		void UnregisterOk() throws Exception {
			Long tournamentId = idHelper.getTournamentIdByName("Calcio");

			mockMvc.perform(post(BASE_URL + "/register/{id}", tournamentId))
				.andExpect(status().isOk());

			mockMvc.perform(delete(BASE_URL + "/unregister/{id}", tournamentId))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.message", equalTo("Utilisateur bien désinscrire du tournoi")));

		}

		@Test
		void noFoundTournoiOfUser() throws Exception {
			Long tournamentId = idHelper.getTournamentIdByName("Calcio");

			mockMvc.perform(delete(BASE_URL + "/unregister/{id}", tournamentId))
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.message", equalTo("Utilisateur bien désinscrire du tournoi")));

		}

	}

}
