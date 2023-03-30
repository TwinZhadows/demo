package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {
	@GetMapping("/hello")
	public String hello() {
		return "Hello User, have a nice day.";
	}
}