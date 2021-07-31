package com.sapient.bug.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.repository.BugRepository;

@ExtendWith(MockitoExtension.class)
class BugServiceImplTest {

	@InjectMocks
	BugServiceImpl bugServiceImpl;

	@Mock
	BugRepository bugRepository;

	@Mock
	MongoTemplate mongoTemplate;
	
	
	

	@Test
	void testSave() {
		Bug bug = new Bug();
		when(bugRepository.save(bug)).thenReturn(bug);
		String response = bugServiceImpl.create(bug);
		assertEquals(bug.getId(), response);
	}

	@Test
	void testDisplay() {
		List<Bug> bugs = new ArrayList<Bug>();
		Bug bug = new Bug();
		bugs.add(bug);
		when(bugRepository.findAll()).thenReturn(bugs);
		List<Bug> response = bugServiceImpl.display();
		assertEquals(bugs, response);
	}

	@Test
	void testGetById() {
		Optional<Bug> bug = Optional.ofNullable(new Bug());
		String id = "123";
		when(bugRepository.findById(id)).thenReturn(bug);
		Optional<Bug> response = bugServiceImpl.serach(id);
		assertEquals(bug, response);
	}

//	@Test
//	void testupdateBug() {
//		Bug bug =new Bug();
//		bug.setName("test");
//		Update update =new Update();
//		Query query=Query.query(Criteria.where("id").is(bug.getId()));
//		update.set("name","test");
//		when(mongoTemplate.findAndModify(query, update,Bug.class)).thenReturn(bug);
//		bugServiceImpl.update(bug);
//		verify(mongoTemplate,times(1)).findAndModify(query, update,bug.getClass());
//	}

//	@Test
//	void testfilterBugs() {
//		int page = 0;
//		int size = 2;
//		Bug bug=new Bug();
//		Pageable paging = PageRequest.of(page, size);
//		Query query = new Query().with(paging);
//		query.addCriteria(Criteria.where("name").regex("name", "i"));
//		List<Bug> list=new ArrayList<Bug>();
//		list.add(bug);
//		when(mongoTemplate.find(query, Bug.class)).thenReturn(list);
//		Page<Bug>pages=new PageImpl<Bug>(list);
//		
//		Object longSupplier;
//		when(() -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Bug.class)).thenReturn(longSupplier);
//		Page<Bug>returnedBug=bugServiceImpl.filterBugs(bug.getName(), page, size);
//		assertEquals(returnedBug,pages);
//
//	}

	@Test
	void testdisplayPaginatedBugs() {
		Pageable paging = PageRequest.of(0, 5);
		List<Bug> bugs = new ArrayList<Bug>();
		Page<Bug> pages = new PageImpl<Bug>(bugs);
		when(bugRepository.findAll(paging)).thenReturn(pages);
		Page<Bug> returnedPage = bugServiceImpl.displayPaginatedBugs(0, 5);
		assertEquals(returnedPage, pages);
	}

	@Test
	void testgetAllBugsByProjectId() {
		Bug bug=new Bug();
		bug.setProjectId("12345");
		Pageable paging = PageRequest.of(0,5);
		List<Bug> bugs = new ArrayList<Bug>();
		Page<Bug> pages = new PageImpl<Bug>(bugs);
		when(bugRepository.findByProjectIdContaining(bug.getProjectId(), paging)).thenReturn(pages);
		Page<Bug> returnedPages=bugServiceImpl.getAllBugsByProjectId(bug.getProjectId(), 0, 5);
		assertEquals(pages, returnedPages);
		
	}
	
	@Test
	void testfilterBugByProjectIdAndName() {
		Bug bug=new Bug();
		bug.setProjectId("12345");
		bug.setName("Bug 1");
		Pageable paging = PageRequest.of(0,5);
		List<Bug> bugs = new ArrayList<Bug>();
		Page<Bug> pages = new PageImpl<Bug>(bugs);
		when(bugRepository.findByProjectIdAndNameIgnoreCaseContaining(bug.getProjectId(),bug.getName(), paging)).thenReturn(pages);
		Page<Bug> returnedPages=bugServiceImpl.filterBugByProjectIdAndName(bug.getProjectId(),bug.getName(), 0, 5);
		assertEquals(returnedPages,pages);
	}

}
