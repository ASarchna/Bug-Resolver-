package com.example.bug.project.email.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.bug.project.email.modal.Mail;
import com.example.bug.project.email.service.IMailService;
@ExtendWith(MockitoExtension.class)
class EmailControllerTest {
	
	@InjectMocks
	EmailController emailController;
	
	@Mock 
	IMailService iMailService;
	

	@Test
	void testEmailController() {
		Mail mail=new Mail();
		mail.setMailFrom("bhartiarchna4@gmail.com");
		mail.setMailSubject("this is mail subject");
		doNothing().when(iMailService).sendmail(mail);
		emailController.sendMail(mail);
		verify(iMailService,times(1)).sendmail(mail);
		
	}

}
