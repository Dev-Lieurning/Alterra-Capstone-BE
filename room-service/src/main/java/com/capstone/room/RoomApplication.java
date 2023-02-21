package com.capstone.room;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomApplication.class, args);
	}

}
