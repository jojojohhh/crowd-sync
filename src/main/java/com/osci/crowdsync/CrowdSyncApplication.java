package com.osci.crowdsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrowdSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdSyncApplication.class, args);
	}

}
