package com.sapient.bug.project.service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
 
@Service
public class EmailServiceImpl implements IEmailService {
	
	@Async
	@Override
	public void sendBugUpdate(String ownerEmail, String bugName) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("mailTo", ownerEmail);
		jsonData.put("mailFrom", "SapientSuraj@gmail.com");
		jsonData.put("mailSubject", "Bug Updated");
		jsonData.put("mailContent", "Bug" + " \"" + bugName + "\" " + " has Been updated");
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonData, headers);
		restTemplate.postForObject("http://localhost:8087/api/email", request, String.class);
	}
 
	
 
}
