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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.service.IBugService;
import com.sapient.bug.project.service.IEmailService;

/**
 * 
 * @author Archna
 *
 */
@CrossOrigin
@RestController
public class BugController {

	@Autowired
	IBugService iBugService;

	@Autowired
	IEmailService iEmailService;

	/**
	 * API for creating new Bug
	 * 
	 * @param bug
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/api/bug")
	public String createBug(@Valid @RequestBody Bug bug) {
		return iBugService.create(bug);
	}

	/**
	 * API for displaying all Bug Details
	 * 
	 * @return
	 */
	@GetMapping("/api/bug")
	public List<Bug> displayBug() {
		return iBugService.display();
	}

	/**
	 * API for Displaying particular Bug Details
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/api/bug/{id}")
	public Optional<Bug> findBugById(@PathVariable String id) {

		return iBugService.serach(id);
	}

	@PutMapping("/api/bug/{id}")
	public void updateBug(@RequestBody Bug bug, @PathVariable String id) {
		bug.setId(id);
		iBugService.update(bug);
	}

	@GetMapping("/api/bug/filter/{name}")
	public Page<Bug> filterBugs(@PathVariable String name, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "2") int pageSize) {
		return iBugService.filterBugs(name, pageNo, pageSize);
	}

	@GetMapping("/api/bug/pagination")
	public Page<Bug> displaypaginatedBugs(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "2") int pageSize) {
		return iBugService.displayPaginatedBugs(pageNo, pageSize);
	}

	@GetMapping("/api/bug/project/{projectId}")
	public Page<Bug> getAllBugsByProjectId(@PathVariable("projectId") String projectId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "2") int pageSize) {
		return iBugService.getAllBugsByProjectId(projectId, pageNo, pageSize);
	}

	@GetMapping("/api/bug/filter3/{projectId}/{name}")
	public Page<Bug> filterBugByProjectIdAndName(@PathVariable(name = "projectId") String projectId,
			@PathVariable(name = "name") String name,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "2") int pageSize) {
		return iBugService.filterBugByProjectIdAndName(projectId, name,pageNo,pageSize);
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
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
