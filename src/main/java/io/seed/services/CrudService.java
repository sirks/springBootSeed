package io.seed.services;

import io.seed.entities.Access;
import io.seed.entities.BaseEntity;
import io.seed.repository.Dao;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;
import static io.seed.app.ApiException.apiException;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class CrudService {

	@Autowired
	protected Dao dao;
	@Value("${default.session.timeout.minutes:30}")
	private int DEFAULT_SESSION_TIMEOUT;

	private Access authorize(String token) {
		if (token == null) {
			throw apiException(UNAUTHORIZED, "No token");
		}
		Map<String, Object> params = newHashMap();
		params.put("token", token);
		List<Access> accesses = dao.listByNamedQuery("Access.token", params);
		if (accesses.isEmpty()) {
			throw apiException(UNAUTHORIZED, "invalid token");
		}
		return accesses.get(0);
	}

	@Transactional(readOnly = true)
	public <T extends BaseEntity> T findId(Class<T> clazz, String id, String token) {
		authorize(token);
		return unproxy(dao.getRequired(clazz, id));
	}

	@Transactional(readOnly = true)
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz, String token) {
		authorize(token);
		return unproxy(dao.list(clazz));
	}

	@Transactional
	public <T extends BaseEntity> void delete(Class<T> clazz, String id, String token) {
		authorize(token);
		T entity = dao.getRequired(clazz, id);
		dao.delete(entity);
	}

	@Transactional
	public <T extends BaseEntity> T save(Class<T> clazz, T bean, String token) {
		authorize(token);
		if (bean.getId() == null) {
			dao.persist(bean);
			return bean;
		}
		dao.getRequired(clazz, bean.getId());
		dao.merge(bean);
		return bean;
	}

	private <T extends BaseEntity> T unproxy(T proxied) {
		T entity = proxied;
		if (entity != null && entity instanceof HibernateProxy) {
			Hibernate.initialize(entity);
			entity = (T) ((HibernateProxy) entity)
					.getHibernateLazyInitializer()
					.getImplementation();
		}
		return entity;
	}

	private <T extends BaseEntity> List<T> unproxy(List<T> proxied) {
		return proxied.stream().map(p -> unproxy(p)).collect(Collectors.toList());
	}
}
