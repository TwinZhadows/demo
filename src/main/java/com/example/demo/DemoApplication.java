package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;  
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication  //entry point of springboot app to start
@ComponentScan({"com.example.demo"})
@RestController

//SpringbootServletInitializer allows app to be configured when launched using Servlet Container
public class DemoApplication extends SpringBootServletInitializer{
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
	{  
		return application.sources(DemoApplication.class);  
	}  
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	   @GetMapping(value = "/")
	   public String hello() {
	      return "Hello World from Tomcat";
	   }

}
