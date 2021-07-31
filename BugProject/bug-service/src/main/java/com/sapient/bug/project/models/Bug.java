package com.sapient.bug.project.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Archna
 *
 */
@Getter
@Setter
public class Bug {

	private String id;
	//@Size(min = 24, max = 24)
	private String projectId;
	/**
	 * Name is mendatory
	 */
	//@NotBlank(message = "Name is Mandatory")
	private String name;
	/**
	 * Owner Name is mandatory
	 */
	//@NotBlank(message = "Owner Name is Mandatory")
	private String ownerName;
	//@Size(min = 20, max = 255)
	private String description;
	@NotBlank(message= "email can't be blank")	
	private String email;
	private LocalDateTime createddate;
	private LocalDateTime completeddate;
	private BUG_STATUS status;
	private BUG_PRIORITY priority;

}
