package io.seed.services;

import io.seed.entities.Access;
import io.seed.entities.Auth;
import io.seed.repository.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Maps.newHashMap;
import static io.seed.app.ApiException.apiException;
import static io.seed.utils.Helper.getRandStringSimple;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class AuthService {

	@Value("${default.session.timeout.minutes:30}")
	private int DEFAULT_SESSION_TIMEOUT;
	
	@Autowired
	protected Dao dao;

	@Transactional
	public Auth register(String username, String password) {
		Auth auth = new Auth(username, password);
		dao.persist(auth);
		return auth;
	}

	@Transactional
	public Auth login(String username, String password) {
		HashMap<String, Object> params = newHashMap();
		params.put("username", username);
		params.put("password", password);
		List<Auth> auths = dao.listByNamedQuery("Auth.login", params);
		if (auths.isEmpty()) {
			throw apiException(UNAUTHORIZED);
		}
		Auth auth = auths.get(0);
		String token = getRandStringSimple(32);
		Access access = new Access(auth, token, now().plusMinutes(DEFAULT_SESSION_TIMEOUT));
		dao.persist(access);
		auth.setToken(token);
		return auth;
	}
}
