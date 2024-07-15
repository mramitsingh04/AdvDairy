package com.generic.khatabook.room.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RoomServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomServiceApplication.class, args);
	}

}
