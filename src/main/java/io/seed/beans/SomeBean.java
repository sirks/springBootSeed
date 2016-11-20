package io.seed.beans;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDateTime.now;

@Component
public class SomeBean{
	public int xxx;
	public String yyy;
	public Boolean bbb;
	public String empty;
	public LocalDateTime ddd;
	public List<String> strings;

	public SomeBean() {
		this.xxx = 9;
		this.yyy = "asdf";
		this.bbb = true;
		this.ddd = now();
		this.strings = newArrayList("xxx", "yyy");
	}
}
