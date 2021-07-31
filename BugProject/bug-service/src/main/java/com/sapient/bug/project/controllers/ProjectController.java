package com.sapient.bug.project.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.service.IProjectService;

/**
 * 
 * @author Archna
 *
 */
@CrossOrigin
@RestController

public class ProjectController {
	@Autowired
	IProjectService projectService;

	/**
	 * API for creating new Project
	 * 
	 * @param project
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/api/project")
	public String createProject(@Valid @RequestBody Project project) {
		return projectService.create(project);
	}

	/**
	 * API for displaying all project details from database
	 * 
	 * @return
	 */
	@GetMapping("/api/project")
	public List<Project> displayProject() {
		return projectService.display();
	}
	
	@GetMapping("/api/project/pagination")
	public Page<Project> displaypaginatedProject(@RequestParam(defaultValue = "0")int pageNo,
			@RequestParam(defaultValue = "3")int pageSize){
		return projectService.displayPaginatedProject(pageNo, pageSize);
	}

	/**
	 * APi for updating particular project
	 * 
	 * @param project
	 * @param projectid
	 */
	
	@PutMapping("/api/project/{id}")
	 public void updateProject(@RequestBody Project project, @PathVariable String id) {
		  project.setId(id);
		  projectService.update(project);
	}

	/**
	 * API to display particular project details
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/api/project/id/{id}")
	public Optional<Project> findProjectById(@PathVariable String id) {
		return projectService.findById(id);
	}

	/**
	 * API to Display those project whose name is searched.
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/api/project/name/{name}")
	public List<Project> findProjectsByName(@PathVariable String name) {
		return projectService.findProjectByName(name);
	}
	
	@GetMapping("/api/project/filter/{name}")
	public Page<Project> filterProjects(@RequestParam(defaultValue = "0")int pageNo,
			@RequestParam(defaultValue = "3")int pageSize,@PathVariable String name) {
		return projectService.filterProject(name,pageNo,pageSize);
	}
    
	
	

	/**
	 * Exception Handler for bad request
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			org.springframework.web.bind.MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
