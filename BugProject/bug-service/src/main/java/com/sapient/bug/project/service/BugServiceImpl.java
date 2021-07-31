package com.sapient.bug.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.repository.BugRepository;

/**
 * 
 * @author Archna
 *
 */
@Service
public class BugServiceImpl implements IBugService {

	@Autowired
	BugRepository bugRepository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	IEmailService iEmailService;

	/**
	 * method to create new Bug in Database
	 */
	@Override
	public String create(Bug bug) {
		bug.setCreateddate(LocalDateTime.now());
		return bugRepository.save(bug).getId();

	}

	/**
	 * method to display all Bug from database
	 */
	@Override
	public List<Bug> display() {
		
		return bugRepository.findAll();
	}

	/**
	 * method to display particular bug from database
	 */
	@Override
	public Optional<Bug> serach(String id) {

		return bugRepository.findById(id);
	}

	@Override
	public void update(Bug bug) {
		Query query = Query.query(Criteria.where("id").is(bug.getId()));
		Update update = new Update();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(bug, Map.class);
		for (Map.Entry<String, Object> updates : map.entrySet()) {
			if (updates.getValue() != null)
				update.set(updates.getKey(), updates.getValue());
		}
		mongoTemplate.findAndModify(query, update, bug.getClass());
	    iEmailService.sendBugUpdate(bug.getEmail(),bug.getName());
		
	}

	@Override
	public Page<Bug> filterBugs(String name, int pageNo, int pageSize) {
		int page = 0;
		int size = 2;
		Pageable paging = PageRequest.of(page, size);
		Query query = new Query().with(paging);
		query.addCriteria(Criteria.where("name").regex(name, "i"));
		List<Bug> list = (List<Bug>) mongoTemplate.find(query, Bug.class);
		Page<Bug> pageBug = (Page<Bug>) PageableExecutionUtils.getPage((List<Bug>) list, paging,
				() -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Bug.class));

		return pageBug;
	}
	@Override
	public Page<Bug> displayPaginatedBugs(int pageNo, int pageSize) {
		
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Bug> pages = bugRepository.findAll(paging);
		return pages;

	}
	
	@Override
	public Page<Bug> getAllBugsByProjectId(String projectId,int pageNo,int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return bugRepository.findByProjectIdContaining(projectId, paging);
	}

	@Override
	public Page<Bug> filterBugByProjectIdAndName(String projectId, String name,int pageNo,int pageSize) {
		Pageable paging = PageRequest.of(pageNo,pageSize);
		return bugRepository.findByProjectIdAndNameIgnoreCaseContaining(projectId, name, paging);}

}
