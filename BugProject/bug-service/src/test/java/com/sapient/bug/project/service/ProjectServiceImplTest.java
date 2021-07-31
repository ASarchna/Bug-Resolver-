package com.sapient.bug.project.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

	@InjectMocks
	ProjectServiceImpl projectServiceImpl;

	@Mock
	ProjectRepository projectRepositoy;

	@Test
	void testSave() {
		Project project = new Project();
		when(projectRepositoy.save(project)).thenReturn(project);
		String response = projectServiceImpl.create(project);
		assertEquals(response, project.getId());
	}

	@Test
	void testDisplay() {
		List<Project> projects = new ArrayList<Project>();
		Project project = new Project();
		projects.add(project);
		when(projectRepositoy.findAll()).thenReturn(projects);
		List<Project> response = projectServiceImpl.display();
		assertEquals(projects, response);
	}

	@Test
	void testGetById() {
		Optional<Project> project = Optional.ofNullable(new Project());
		String id = "123";
		when(projectRepositoy.findById(id)).thenReturn(project);
		Optional<Project> response = projectServiceImpl.findById(id);
		assertEquals(project, response);
	}

	@Test
	void testfindProjectByName() {
		List<Project> projects = new ArrayList<Project>();
		Project project = new Project();
		projects.add(project);
		when(projectRepositoy.findByName("Archna")).thenReturn(projects);
		List<Project> response = projectServiceImpl.findProjectByName("Archna");
		assertEquals(projects, response);
	}
	
	@Test
	void testdisplayPaginatedProject() {
		Project project=new Project();
		project.setId("12345");
		project.setName("Bug 1");
		Pageable paging = PageRequest.of(0,5);
		List<Project> bugs = new ArrayList<Project>();
		Page<Project> pages =new PageImpl<Project>(bugs);
		when(projectRepositoy.findAll(paging)).thenReturn(pages);
		Page<Project> returnedPages = projectServiceImpl.displayPaginatedProject(0,5);
		assertEquals(returnedPages, pages);
		
	}

	
}
