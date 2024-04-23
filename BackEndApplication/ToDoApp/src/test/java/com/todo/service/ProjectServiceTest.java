package com.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.todo.modal.Project;
import com.todo.repository.ProjectRepository;
import com.todo.repository.ToDoRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class ProjectServiceTest {

	private static final String DATE = "2024-04-22";

	private static final String TITLE = "Project 1";

	private static final long PROJECT_ID = 0;

	ProjectService projectService;
	
	@Mock
	ToDoRepository mockTodoRepository;
	@Mock
	ProjectRepository mockProjectRepository;
	
	@BeforeAll
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		projectService=new ProjectService(mockProjectRepository, mockTodoRepository);
	}
	
	@Test
	public void getProjects() {
		List<Project> projects= new ArrayList<Project>();
		Project project=new Project();
		project.setProId(PROJECT_ID);
		project.setTitle(TITLE);
		project.setCreatedDate(DATE);
		projects.add(project);
		when(mockProjectRepository.findAll()).thenReturn(projects);
		assertEquals(projects, projectService.getProjects());
	}
	
	@Test
	public void addProjects() {
		projectService.addProject(TITLE, DATE);
		verify(mockProjectRepository, times(1)).save(any(Project.class));
	}
	
	@Test
	public void updateProject_If_ProjectId_Is_Present() {
		Project project=new Project();
		project.setProId(PROJECT_ID);
		project.setTitle(TITLE);
		project.setCreatedDate(DATE);
		Optional<Project> projects = Optional.of(project);
		projectService.updateProject(PROJECT_ID, TITLE);
		when(mockProjectRepository.findById(PROJECT_ID)).thenReturn(projects);
		verify(mockProjectRepository, times(1)).findById(PROJECT_ID);
		verify(mockProjectRepository, times(1)).save(any(Project.class));
    }
	
	@Test
	public void updateProject_If_ProjectId_Not_Present() {
		Optional<Project> projects = Optional.empty();
		projectService.updateProject(PROJECT_ID, TITLE);
		when(mockProjectRepository.findById(PROJECT_ID)).thenReturn(projects);
		verify(mockProjectRepository, times(2)).findById(PROJECT_ID);
		verify(mockProjectRepository, times(2)).save(any(Project.class));
    }

	@Test
	public void deleteProject() {
		projectService.deleteProject(PROJECT_ID);
		verify(mockTodoRepository, times(1)).deleteByProjectProjectId(PROJECT_ID);
        verify(mockProjectRepository, times(1)).deleteById(PROJECT_ID);
    }
}
