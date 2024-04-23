package com.todo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.modal.Project;
import com.todo.repository.ProjectRepository;
import com.todo.repository.ToDoRepository;

@Service
@Transactional
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final ToDoRepository todoRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ToDoRepository todoRepository) {
        this.projectRepository = projectRepository;
        this.todoRepository = todoRepository;
    }
    

    public List<Project> getProjects() {
        return (List<Project>) projectRepository.findAll();
    }
    
    public void addProject(String title, String createdDate) {
        Project project = new Project();
        project.setTitle(title);
        project.setCreatedDate(createdDate);
        projectRepository.save(project);
    }
    
    public void updateProject(long projectId, String title) {
        projectRepository.findById(projectId).ifPresent(project -> {
            project.setTitle(title);
            projectRepository.save(project);
        });
    }
    
    public void deleteProject(long projectId) {
		// To remove all associated todo's
    	todoRepository.deleteByProjectProjectId(projectId);
    	projectRepository.deleteById(projectId);
    }
}
