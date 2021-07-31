package com.example.bug.project.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bug.project.email.modal.Mail;
import com.example.bug.project.email.service.IMailService;
@CrossOrigin
@RestController
public class EmailController {
	
	@Autowired
	IMailService iMailService;
	
	@PostMapping("/api/email")
	public void sendMail(@RequestBody Mail mail) {
		iMailService.sendmail(mail);
		
	}
	

}
