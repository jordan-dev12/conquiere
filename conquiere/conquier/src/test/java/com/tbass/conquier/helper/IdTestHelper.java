package com.tbass.conquier.helper;

import static java.util.Collections.emptyMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.tbass.conquier.entity.TournamentEntity;
import com.tbass.conquier.entity.UserEntity;

@Component
public class IdTestHelper {

	private final Map<Class<?>, Map<String, Object>> ids = new HashMap<>();

	public IdTestHelper register(Class<?> type, String hint, Object value) {
		ids.putIfAbsent(type, new HashMap<>());
		ids.get(type).put(hint, value);

		return this;
	}

	public IdTestHelper reset() {
		ids.clear();
		return this;
	}

	public IdTestHelper registerUser(String hint, Long value) {
		return register(UserEntity.class, hint, value);
	}

	public IdTestHelper registerTournament(String hint, Long value) {
		return register(TournamentEntity.class, hint, value);
	}

	public Object getId(Class<?> type, String hint) {
		return ids.getOrDefault(type, emptyMap()).get(hint);
	}

	public Long getUserIdByEmail(String hint) {
		return Long.valueOf(Objects.toString(getId(UserEntity.class, hint)));
	}

	public Long getTournamentIdByName(String hint) {
		return Long.valueOf(Objects.toString(getId(TournamentEntity.class, hint)));
	}

}
