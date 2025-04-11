package com.tbass.conquier.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TOURNAMENT")
public class TournamentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Le nom du tournoi est obligatoire")
	private String name;

	private String description;

	@NotNull(message = "La date de creation du tournoi est obligatoire")
	@Column(name = "date_issued")
	private LocalDate dateIssued;

	@NotNull(message = "la date de déroulement du tournoi est obligatoire")
	@Column(name = "event_date")
	private LocalDate eventDate;

	@ManyToOne
	@JoinColumn(name = "creator_id", nullable = false)
	private UserEntity creator;

	@ManyToMany(mappedBy = "tournaments")
	private Set<UserEntity> participants = new HashSet<>();

}
