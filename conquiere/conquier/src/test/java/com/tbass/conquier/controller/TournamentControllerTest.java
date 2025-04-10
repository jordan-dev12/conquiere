package com.tbass.conquier.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
import com.tbass.conquier.dtos.TournamentRequestDto;

@Transactional
@WithMockUser(username = "admin@admin.com", roles = { "ADMIN" })
public class TournamentControllerTest extends AbstractIntegrationTest {

	private final String BASE_URL = "/api/tournoi";
	private static final LocalDate DATE_ISSUE = LocalDate.now();

	@BeforeEach
	public void initTournamentTest() {
		userHelper.users().deleteAll();
		tournamentHelper.defaultDataSet();
	}

	@Nested
	@DisplayName("Get Tournoi By ID")
	class GetByID {

		@Test
		void found() throws Exception {
			Long tournamentId = idHelper.getTournamentIdByName("Calcio");

			mockMvc.perform(get(BASE_URL + "/get/{id}", tournamentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(tournamentId.intValue())))
				.andExpect(jsonPath("$.name", is("Calcio")))
				.andExpect(jsonPath("$.dateIssued", is(DATE_ISSUE.format(getFormatter()))))
				.andExpect(jsonPath("$.eventDate", is(DATE_ISSUE.plusDays(7).format(getFormatter()))))
				.andExpect(jsonPath("$.description", is("Tournoi d'été")))
				.andExpect(jsonPath("$.adminId").isNotEmpty());

		}

		@Test
		@WithMockUser(username = "admin", roles = { "ADMIN" })
		void noFound() throws Exception {

			mockMvc.perform(get(BASE_URL + "/get/{id}", 25))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.detail", equalTo("Tournoir non trouvé avec l'ID: 25")));

		}

	}

	@Nested
	@DisplayName("Create Tournoi")
	class CreateUser {

		@Test
		void createTournoiOk() throws Exception {

			LocalDate evenDate = DATE_ISSUE.plusDays(5);
			Long adminId = idHelper.getUserIdByEmail("admin@admin.com");

			TournamentRequestDto tournamentRequestDto = TournamentRequestDto.builder()
				.name("Grand prix")
				.description("Premier experience")
				.eventDate(evenDate)
				.build();

			mockMvc.perform(post(BASE_URL + "/create").content(objectMapper.writeValueAsString(tournamentRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name", equalTo("Grand prix")))
				.andExpect(jsonPath("$.description", equalTo("Premier experience")))
				.andExpect(jsonPath("$.dateIssued", is(DATE_ISSUE.format(getFormatter()))))
				.andExpect(jsonPath("$.eventDate", is(evenDate.format(getFormatter()))))
				.andExpect(jsonPath("$.adminId", equalTo(adminId.intValue())));

		}

		@Test
		void noCreateTournoi() throws Exception {

			LocalDate evenDate = DATE_ISSUE.minusDays(7);
			TournamentRequestDto tournamentRequestDto = TournamentRequestDto.builder()
				.name("Conforama")
				.description("1er competitions du valle")
				.eventDate(evenDate)
				.build();

			mockMvc.perform(post(BASE_URL + "/create").content(objectMapper.writeValueAsString(tournamentRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail", equalTo("La date du tournoi doit être postérieure ou égale à la date d'aujourd'hui")));

		}

	}

}
