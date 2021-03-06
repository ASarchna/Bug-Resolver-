package com.example.bug.project.email.modal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
	private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contentType;
 
    private List< Object > attachments;
    public Mail() {
        contentType = "text/plain";
    }
    public void setAttachments(List < Object > attachments) {
        this.attachments = attachments;
    }

}
