package io.seed.resources;

import io.seed.entities.BaseEntity;
import io.seed.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;


public abstract class CrudResource<T extends BaseEntity> {

	@Autowired
	private CrudService service;
	
	private Class<T> clazz;

	public CrudResource(Class<T> clazz) {
		this.clazz = clazz;
	}

	@RequestMapping(
			value = "{id}",
			method = GET,
			produces = APPLICATION_JSON_VALUE)
	public T findId(@PathVariable("id") String id, @RequestHeader("token") String token) {
		return service.findId(clazz,id,token);
	}

	@RequestMapping(
			method = GET,
			produces = APPLICATION_JSON_VALUE)
	public List<T> findAll(@RequestHeader("token") String token) {
		return service.findAll(clazz, token);
	}

	@RequestMapping(
			value = "{id}",
			method = DELETE,
			produces = APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id") String id, @RequestHeader("token") String token) {
		service.delete(clazz, id, token);
	}

	@RequestMapping(
			value = "save",
			method = POST,
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE)
	public T save(T bean, @RequestHeader("token") String token) {
		return service.save(clazz, bean, token);
	}
}
