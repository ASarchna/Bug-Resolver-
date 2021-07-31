package com.sapient.bug.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.sapient.bug.project.models.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
	/**
	 * pagination and sorting
	 * 
	 * @param name
	 * @return
	 */
	public List<Project> findByName(String name);
}
