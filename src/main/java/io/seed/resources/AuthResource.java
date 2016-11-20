package io.seed.resources;

import io.seed.entities.Auth;
import io.seed.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("auths")
public class AuthResource extends CrudResource<Auth> {

	@Autowired
	private AuthService service;

	public AuthResource() {
		super(Auth.class);
	}

	@RequestMapping(value = "register", produces = APPLICATION_JSON_VALUE)
	public Auth register(@RequestParam("username") String username, @RequestParam("password") String password) {
		return service.register(username, password);
	}


	@RequestMapping(value = "login", produces = APPLICATION_JSON_VALUE)
	public Auth login(@RequestParam("username") String username, @RequestParam("password") String password) {
		return service.login(username, password);
	}
}