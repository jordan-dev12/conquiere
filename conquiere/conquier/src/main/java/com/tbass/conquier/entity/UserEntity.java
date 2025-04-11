package com.tbass.conquier.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "USERS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Le nom est obligatoire")
	private String name;

	@NotBlank(message = "Le prénom est obligatoire")
	private String surname;

	@NotBlank(message = "L'email est obligatoire")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Mot de passe est obligatoire")
	private String password;

	@NotNull
	private LocalDate birthdate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
	private Collection<String> roles = new ArrayList<>();

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private List<TournamentEntity> createdTournaments = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "users_tournament_registration", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tournament_id"))
	private Set<TournamentEntity> tournaments = new HashSet<>();

}
