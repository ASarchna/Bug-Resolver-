package com.sapient.bug.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.sapient.bug.project.controllers.BugController;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.service.BugServiceImpl;
import com.sapient.bug.project.service.IBugService;
/**
 * 
 * @author Archna
 *
 */
@ExtendWith(MockitoExtension.class)
class BugControllerTest {
	
	@InjectMocks
	private BugController bugController;
	
	@Mock
	IBugService iBugService;

	@Test
	void testSaveBug() {
		Bug bug = new Bug();
		when(iBugService.create(bug)).thenReturn("123");
		String found=bugController.createBug(bug);
		assertEquals("123",found);
	}
	
	@Test
	void testGetBug() {
		Bug bug = new Bug();
		List<Bug> bugs=new ArrayList<Bug>();
        bugs.add(bug);
        when(iBugService.display()).thenReturn(bugs);
        List<Bug> buglist=bugController.displayBug();
        assertEquals(bugs,buglist );
	}
	
	
	@Test
	void testBugById() {
		Optional<Bug> bug=Optional.ofNullable(new Bug());
		String id="12345";
		when(iBugService.serach(id)).thenReturn(bug);
		Optional<Bug> bug1= bugController.findBugById(id);
		assertEquals(bug,bug1);
		
	}
	
	@Test
	void testupdateBug() {
		Bug bug =new Bug();
		String id="tested";
		doNothing().when(iBugService).update(bug);
		bugController.updateBug(bug, id);
		verify(iBugService,times(1)).update(bug);
		
		
	}
	
	@Test 
	void testfilterBugs() {
		Bug bug=new Bug();
		bug.setName("Archna");
		int pageNo=1;
		int pageSize=5;
		List<Bug> bugs=new ArrayList<Bug>();
		bugs.add(bug);
		Page<Bug>pages=new PageImpl<Bug>(bugs);
		when(iBugService.filterBugs(bug.getName(), pageNo, pageSize)).thenReturn(pages);
		Page<Bug> returnedPage =bugController.filterBugs(bug.getName(), pageNo, pageSize);
		assertEquals(returnedPage, pages);
		
	}
	
	@Test
	void testdisplayPaginatedBugs() {
		Bug bug=new Bug();
		bug.setName("Archna");
		int pageNo=1;
		int pageSize=5;
		List<Bug> bugs=new ArrayList<Bug>();
		bugs.add(bug);
		Page<Bug>pages=new PageImpl<Bug>(bugs);
		when(iBugService.displayPaginatedBugs(pageNo, pageSize)).thenReturn(pages);
		Page<Bug> returnedPage=bugController.displaypaginatedBugs(pageNo, pageSize);
		assertEquals(returnedPage,pages);
		
	}
	
	@Test
	void testfilterBugByProjectIdAndName() {
		Bug bug=new Bug();
		bug.setName("Archna");
		bug.setProjectId("12345");
		int pageNo=1;
		int pageSize=5;
		List<Bug> bugs=new ArrayList<Bug>();
		bugs.add(bug);
		Page<Bug>pages=new PageImpl<Bug>(bugs);
		when(iBugService.filterBugByProjectIdAndName(bug.getProjectId(),bug.getName(), pageNo, pageSize)).thenReturn(pages);
		Page<Bug>returnedPage =bugController.filterBugByProjectIdAndName(bug.getProjectId(), bug.getName(), pageNo, pageSize);
		assertEquals(returnedPage,pages);
		
	}
	@Test
	void testgetAllBugsByProjectId() {
		Bug bug=new Bug();
		bug.setName("Archna");
		bug.setProjectId("12345");
		int pageNo=1;
		int pageSize=5;
		List<Bug> bugs=new ArrayList<Bug>();
		bugs.add(bug);
		Page<Bug>pages=new PageImpl<Bug>(bugs);
		bugs.add(bug);
		when(iBugService.getAllBugsByProjectId(bug.getProjectId(), pageNo, pageSize)).thenReturn(pages);
		Page<Bug>returnedPage=bugController.getAllBugsByProjectId(bug.getProjectId(), pageNo, pageSize);
		assertEquals(returnedPage,pages);
	}
	
	
	

}
