package com.tbass.conquier.helper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.repositories.TournamentRepository;
import com.tbass.conquier.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TournamentTestHelper {

	private final TournamentRepository tournamentRepository;
	private final UserRepository userRepository;
	private final IdTestHelper idTestHelper;
	private final UserTestHelper userHelper;

	public TournamentsStep tournaments() {
		return new TournamentsStep();
	}

	public void defaultDataSet() {

		tournaments().deleteAll();
		userHelper.users().create().regularAdmin("admin");
		tournaments().create()
			.name("Calcio")
			.description("Tournoi d'été")
			.save();

	}

	@RequiredArgsConstructor
	public class TournamentsStep {

		public TournamentCreateStep create() {
			return new TournamentCreateStep(this);

		}

		public TournamentsStep deleteAll() {
			tournamentRepository.deleteAll();
			return this;
		}

	}

	@RequiredArgsConstructor
	public class TournamentCreateStep {

		private final TournamentsStep tournamentsStep;
		private String name;
		private String description;
		private LocalDate eventDate = LocalDate.now().plusDays(7);
		private LocalDate dateIssue = LocalDate.now();
		private UserEntity creator;

		public TournamentStep reguralTournoi(String name) {
			return tournaments().create()
				.name(name)
				.description(name)
				.save();

		}

		public TournamentCreateStep name(String name) {
			this.name = name;
			return this;
		}

		public TournamentCreateStep description(String description) {
			this.description = description;
			return this;
		}

		public TournamentCreateStep eventDate(LocalDate eventDate) {
			this.eventDate = eventDate;
			return this;
		}

		public TournamentCreateStep dateIssue(LocalDate dateIssue) {
			this.dateIssue = dateIssue;
			return this;
		}

		public TournamentCreateStep creator(UserEntity creator) {
			this.creator = creator;
			return this;
		}

		public TournamentStep save() {

			if (creator == null) {
				creator = userRepository.findByEmail("admin@admin.com")
					.orElseThrow(() -> new RuntimeException("Admin user not found"));
			}
			TournamentEntity tournamentEntity = new TournamentEntity();
			tournamentEntity.setName(name);
			tournamentEntity.setDescription(description);
			tournamentEntity.setDateIssued(dateIssue);
			tournamentEntity.setEventDate(eventDate);
			tournamentEntity.setCreator(creator);
			TournamentEntity tournamentResponse = tournamentRepository.save(tournamentEntity);
			idTestHelper.registerTournament(name, tournamentResponse.getId());

			return new TournamentStep(tournamentResponse, tournamentsStep);

		}

	}

	@RequiredArgsConstructor
	public class TournamentStep {

		private final TournamentEntity tournamentEntity;
		private final TournamentsStep tournamentsStep;

		public TournamentsStep and() {
			return this.tournamentsStep;
		}

		public TournamentEntity get() {
			return this.tournamentEntity;
		}

		public Long getId() {
			return get().getId();
		}

	}

}
