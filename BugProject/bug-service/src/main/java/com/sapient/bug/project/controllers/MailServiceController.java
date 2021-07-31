package com.sapient.bug.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sapient.bug.project.models.Mail;
@CrossOrigin
@RestController
public class MailServiceController {
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;


	@PostMapping("/api/mailrequest")
	public Mail sendMail(@RequestBody Mail mail) {
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		System.out.println("hello" + mail.getMailFrom());

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Mail> httpEntity = new HttpEntity<>(mail, headers);
		
		this.restTemplate.exchange("http://SERVER/api/email", HttpMethod.POST, httpEntity, Mail.class);
		return mail;

	}
	}
