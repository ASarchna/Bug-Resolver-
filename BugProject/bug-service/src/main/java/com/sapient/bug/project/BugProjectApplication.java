package com.sapient.bug.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Archna
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
@CrossOrigin
@RibbonClient(name ="SERVER")
public class BugProjectApplication {

	public static void main(String[] args) {
		/**
		 * Staring of Project
		 */
		SpringApplication.run(BugProjectApplication.class, args);
	}

}
