package io.seed.resources;

import io.seed.beans.SomeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@RestController
@RequestMapping("hello")
public class HelloResource {

	@Autowired
	private SomeBean someBean;

	@RequestMapping("world")
	public String world() {
		return "hello world";
	}

	@RequestMapping("someBean")
	public List<SomeBean> someBean() {
		return newArrayList(someBean, someBean);
	}
}