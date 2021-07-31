package com.sapient.bug.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.repository.ProjectRepository;

/**
 * 
 * @author Archna
 *
 */
@Service
public class ProjectServiceImpl implements IProjectService {

	@Autowired
	ProjectRepository projectService;
	
	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * method to create new Project in database.
	 */
	@Override
	public String create(Project project) {
		LocalDateTime localdateTime = LocalDateTime.now();
		project.setCreatedDate(localdateTime);
		Project project2 = projectService.save(project);
		return project2.getId();
	}

	/**
	 * method to display all projects from database
	 */
	@Override
	public List<Project> display() {
		return projectService.findAll();
	}

	/**
	 * method to update particular Project Details in database.
	 */
	@Override
	public void update(Project project) {
		Query query = Query.query(Criteria.where("id").is(project.getId()));
		Update update = new Update();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(project, Map.class);
		for (Map.Entry<String, Object> updates : map.entrySet()) {
			if (updates.getValue() != null)
				update.set(updates.getKey(), updates.getValue());
		}
		mongoTemplate.findAndModify(query, update, project.getClass());
		
		
	}

	/**
	 * method to display particular Project
	 */
	@Override
	public Optional<Project> findById(String id) {
		return projectService.findById(id);
	}

	/**
	 * method to display ALl Project whose prjectName is searched.
	 */
	@Override
	public List<Project> findProjectByName(String name) {
		return projectService.findByName(name);
	}
	@Override
	public Page<Project> filterProject(String name,int pageNo,int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name,"i"));
		List<Project> projects = mongoTemplate.find(query,Project.class);
		Page<Project> pageBug = (Page<Project>) PageableExecutionUtils.getPage((List<Project>) projects, paging,
				() -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Project.class));
		return pageBug;
	}
    
	
	@Override
	public Page<Project> displayPaginatedProject(int pageNo, int pageSize) {
		Pageable paging =PageRequest.of(pageNo, pageSize);
		Page<Project>pages=projectService.findAll(paging);
		return pages;
	}

	


}
