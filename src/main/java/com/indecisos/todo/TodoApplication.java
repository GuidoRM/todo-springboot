package com.indecisos.todo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.cloudinary.Cloudinary;

@SpringBootApplication
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoApplication {

	@Autowired
	private Cloudinary cloudinary;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
;