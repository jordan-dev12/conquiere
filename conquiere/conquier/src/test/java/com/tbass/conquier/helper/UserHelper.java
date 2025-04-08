package com.tbass.conquier.helper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.enums.Role;
import com.tbass.conquier.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserHelper {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final IdTestHelper idTestHelper;

	public UserHelper defaultDataSet() {

		users().deleteAll();
		users()
			.create()
			.regularUser("user1")
			.and()
			.create()
			.regularUser("user2")
			.and()
			.create()
			.regularAdmin("admin");

		return this;
	}

	public UsersStep users() {
		return new UsersStep();
	}

	@RequiredArgsConstructor
	public class UsersStep {

		public UsersStep deleteAll() {
			userRepository.deleteAll();
			return this;
		}

		public UserCreateStep create() {
			return new UserCreateStep(this);
		}
	}

	@RequiredArgsConstructor
	public class UserCreateStep {
		private final UsersStep usersStep;
		private String email;
		private String password = "password123";
		private String name;
		private String surname;
		private LocalDate birthdate;
		private Set<String> roles = new HashSet<>();

		public UserStep regularUser(String username) {
			return users()
				.create()
				.surname(username)
				.name(username)
				.email(username + "@user.com")
				.birthdate(LocalDate.of(1998, 03, 12))
				.save();
		}

		public UserStep regularAdmin(String username) {
			return users()
				.create()
				.surname(username)
				.name(username)
				.email(username + "@admin.com")
				.birthdate(LocalDate.of(1998, 06, 02))
				.saveAdmin();
		}

		public UserCreateStep email(String email) {
			this.email = email;
			return this;
		}

		public UserCreateStep name(String name) {
			this.name = name;
			return this;
		}

		public UserCreateStep surname(String surname) {
			this.surname = surname;
			return this;
		}

		public UserCreateStep password(String password) {
			this.password = password;
			return this;
		}

		public UserCreateStep birthdate(LocalDate birthdate) {
			this.birthdate = birthdate;
			return this;
		}

		private UserCreateStep addAdminRole() {

			this.roles.add(Role.ADMIN.getValue());
			return this;
		}

		public UserStep save() {
			UserEntity userEntity = new UserEntity();
			userEntity.setBirthdate(birthdate);
			userEntity.setEmail(email);
			userEntity.setName(name);
			userEntity.setSurname(surname);
			userEntity.setPassword(passwordEncoder.encode(password));
			roles.add(Role.USER.getValue());
			userEntity.setRoles(roles);

			UserEntity savedUser = userRepository.save(userEntity);
			idTestHelper.registerUser(email, savedUser.getId());
			return new UserStep(usersStep, savedUser);
		}

		public UserStep saveAdmin() {
			return addAdminRole().save();
		}

	}

	@RequiredArgsConstructor
	public class UserStep {
		private final UsersStep usersStep;
		private final UserEntity userEntity;

		public UsersStep and() {
			return usersStep;
		}

		public UserEntity get() {
			return userEntity;
		}

		public Long getId() {
			return get().getId();
		}
	}

}
