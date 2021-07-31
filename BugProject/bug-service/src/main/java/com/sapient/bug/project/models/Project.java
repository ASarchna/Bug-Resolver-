package com.sapient.bug.project.models;

import java.time.LocalDateTime;

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
public class Project {

	private String id;
	/**
	 * Name can't be blank
	 */
	@Size(min = 10, max = 50)
	private String name;
	/**
	 * Description can't be blank
	 */
	@Size(min = 20, max = 255)
	private String description;

	private LocalDateTime createdDate;

	private LocalDateTime completedDate;

}
