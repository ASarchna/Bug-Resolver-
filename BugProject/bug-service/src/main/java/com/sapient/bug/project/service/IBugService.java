package com.sapient.bug.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;

/**
 * 
 * @author Archna
 *
 */
public interface IBugService {

	public String create(Bug bug);

	public List<Bug> display();

	public Optional<Bug> serach(String id);

	public void update(Bug bug);

	public Page<Bug> filterBugs(String name,int pageNo,int pageSize);

	public Page<Bug> displayPaginatedBugs(int pageNo, int pageSize);

	public Page<Bug> getAllBugsByProjectId(String projectId,int pageNo,int pageSize);

	public Page<Bug> filterBugByProjectIdAndName(String projectId, String name,int pageNo,int pageSize);

}
