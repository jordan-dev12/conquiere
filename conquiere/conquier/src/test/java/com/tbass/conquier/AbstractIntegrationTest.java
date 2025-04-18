package com.tbass.conquier;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbass.conquier.helper.IdTestHelper;
import com.tbass.conquier.helper.TournamentTestHelper;
import com.tbass.conquier.helper.UserTestHelper;
import com.tbass.conquier.service.TournamentService;
import com.tbass.conquier.service.UserService;

@SpringBootTest(classes = ConquierApplicationTest.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ComponentScan({ "com.tbass.conquier" })
public abstract class AbstractIntegrationTest {

	@Autowired
	protected TournamentService tournamentService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected IdTestHelper idHelper;

	@Autowired
	protected UserTestHelper userHelper;

	@Autowired
	protected TournamentTestHelper tournamentHelper;

	@BeforeEach
	protected void setup() {
		idHelper.reset();
	}

}
