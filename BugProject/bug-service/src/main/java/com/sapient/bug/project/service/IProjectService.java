package com.sapient.bug.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.sapient.bug.project.models.Project;
/**
 * 
 * @author Archna
 *
 */
public interface IProjectService {
    public String create(Project project);
    public List<Project> display();
	public void update(Project project);
	public Optional<Project> findById(String id);
	public List<Project> findProjectByName(String name);
	public Page<Project> filterProject(String name,int pageNo,int pageSize);
	public Page<Project> displayPaginatedProject(int pageNo, int pageSize);
	
}
