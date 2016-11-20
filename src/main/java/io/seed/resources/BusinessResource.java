package io.seed.resources;

import io.seed.entities.Business;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("businesses")
public class BusinessResource extends CrudResource<Business> {
	public BusinessResource() {
		super(Business.class);
	}
}
