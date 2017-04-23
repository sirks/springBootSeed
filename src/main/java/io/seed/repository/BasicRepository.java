package io.seed.repository;

import io.seed.entities.BaseEntity;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session.LockRequest;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.seed.app.ApiException.apiException;
import static java.util.Optional.of;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class BasicRepository {

	private static final int LOCK_TIMEOUT = 1000 * 60;
	private static final int MAX_LIMIT = 100;

	private static final Logger log = LoggerFactory.getLogger(BasicRepository.class);

	@Autowired
	protected SessionFactory sessionFactory;

	public <E extends BaseEntity, ID extends Serializable> ID persist(E entity) {
		log.debug("Persisting entity {}", entity);
		entity.onSave();
		ID id = (ID) sessionFactory.getCurrentSession().save(entity);
		return id;
	}

	public <E extends BaseEntity, ID extends Serializable> E getRequired(Class<E> entityClass, ID id) {
		E entity = (E) sessionFactory.getCurrentSession().get(entityClass, id);
		if (entity == null) {
			throw apiException(NOT_FOUND, "Entity " + entityClass.getSimpleName() + " not found by id " + id);
		}
		return entity;
	}

	public <E extends BaseEntity> void delete(E entity) {
		log.debug("Deleting entity {}", entity);
		sessionFactory.getCurrentSession().delete(entity);
	}

	public <E extends BaseEntity> void lockForWrite(E entity) {
		LockRequest lockRequest = sessionFactory.getCurrentSession().buildLockRequest(LockOptions.UPGRADE).setLockMode(LockMode.PESSIMISTIC_WRITE).setTimeOut(LOCK_TIMEOUT);
		lockRequest.lock(entity);
	}

	public <E extends BaseEntity> void merge(E entity) {
		log.debug("merging entity {}", entity);
		entity.onSave();
		sessionFactory.getCurrentSession().merge(entity);
	}

	public <E extends BaseEntity, ID extends Serializable> Optional<E> getOptional(Class<E> entityClass, ID id) {
		E entity = (E) sessionFactory.getCurrentSession().get(entityClass, id);
		return of(entity);
	}

	public <E extends BaseEntity> List<E> list(Class<E> entityClass) {
		return sessionFactory.getCurrentSession().createCriteria(entityClass).setMaxResults(MAX_LIMIT).list();
	}

	public <E extends BaseEntity> List<E> listByNamedQuery(String namedQuery, Map<String,Object> params){
		return sessionFactory.getCurrentSession().getNamedQuery(namedQuery).setProperties(params).setMaxResults(MAX_LIMIT).list();
	}
}
