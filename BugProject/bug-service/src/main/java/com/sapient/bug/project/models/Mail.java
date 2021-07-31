package com.sapient.bug.project.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

	private String mailFrom;
    private String mailTo;
    private String mailSubject;
    private String mailContent;
    
}
