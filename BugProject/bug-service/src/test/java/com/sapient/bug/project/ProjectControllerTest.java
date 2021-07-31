package com.sapient.bug.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.sapient.bug.project.controllers.ProjectController;
import com.sapient.bug.project.models.Bug;
import com.sapient.bug.project.models.Project;
import com.sapient.bug.project.service.IProjectService;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {
	@InjectMocks
	private ProjectController projectController;

	@Mock
	IProjectService iProjectService;

	@Test
	void testGetRequest() {

		Project project1 = new Project();
		Project project2 = new Project();
		List<Project> list = new LinkedList<Project>();
		list.add(project1);
		list.add(project2);
		when(iProjectService.display()).thenReturn(list);
		List<Project> allProject = projectController.displayProject();
		assertNotNull(allProject);

	}

	@Test
	void testPostRequest() {
		Project project = new Project();
		project.setName("Archna");
		when(iProjectService.create(project)).thenReturn("123");
		assertEquals("123", projectController.createProject(project));

	}

	@Test
	void testGetProjectById() {
		Optional<Project> project = Optional.ofNullable(new Project());
		String id = "12345";
		when(iProjectService.findById(id)).thenReturn(project);
		Optional<Project> response = projectController.findProjectById(id);
		assertEquals(project, response);
	}

	@Test
	void testGetProjectByName() {
		List<Project> projects = new ArrayList<Project>();
		Project project = new Project();
		projects.add(project);
		String name = "Archna";
		when(iProjectService.findProjectByName(name)).thenReturn(projects);
		List<Project> projects2 = projectController.findProjectsByName(name);
		assertEquals(projects, projects2);
	}
	
	@Test
	void testdisplaypaginatedProject() {
		Project project=new Project();
		project.setName("Archna");
		project.setId("12345");
		int pageNo=1;
		int pageSize=5;
		List<Project> projects=new ArrayList<Project>();
		projects.add(project);
		Page<Project>pages=new PageImpl<Project>(projects);
		when(iProjectService.displayPaginatedProject(pageNo, pageSize)).thenReturn(pages);
		Page<Project> returnedPages =projectController.displaypaginatedProject(pageNo, pageSize);
		assertEquals(returnedPages,pages);
	}
	
	@Test
	void testfilterProjects() {
		Project project=new Project();
		project.setName("Archna");
		project.setId("12345");
		int pageNo=1;
		int pageSize=5;
		List<Project> projects=new ArrayList<Project>();
		projects.add(project);
		Page<Project>pages=new PageImpl<Project>(projects);
		when(iProjectService.filterProject(project.getName(), pageNo, pageSize)).thenReturn(pages);
		Page<Project> returnedPages =projectController.filterProjects(pageNo, pageSize,project.getName());
		assertEquals(returnedPages, pages);
	}
	

}
