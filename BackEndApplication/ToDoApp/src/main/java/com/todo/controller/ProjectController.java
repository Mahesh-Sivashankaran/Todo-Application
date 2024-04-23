package com.todo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todo.modal.Project;
import com.todo.requestModal.ProjectAddRequest;
import com.todo.requestModal.ProjectUpdateRequest;
import com.todo.service.ProjectService;


@RestController
@RequestMapping("/Project")
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;

	
	// Project Controller
	@RequestMapping(value = "/getAllProjects", method=RequestMethod.GET)
	public ResponseEntity<ProjectResponse> getProjects() {
		List<Project> projects = projectService.getProjects();
		ProjectResponse response = new ProjectResponse(projects);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static class ProjectResponse {
        private List<Project> projects;

        public ProjectResponse(List<Project> projects) {
            this.projects = projects;
        }

        public List<Project> getProjects() {
            return projects;
        }

        public void setProjects(List<Project> projects) {
            this.projects = projects;
        }
    }
	
	@RequestMapping(value = "/addProject", method=RequestMethod.POST)
	public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectAddRequest projectAddRequest) {
        String title = projectAddRequest.getTitle();
        String createdDate = projectAddRequest.getCreatedDate();
        
        projectService.addProject(title,createdDate);
		
		return getProjects();
	}
	
	@RequestMapping(value = "/updateProject", method=RequestMethod.PUT)
	public ResponseEntity<ProjectResponse> updateProject(@RequestBody ProjectUpdateRequest projectUpdateRequest) {
		long proId = projectUpdateRequest.getProId();
        String title = projectUpdateRequest.getTitle();
        logger.info("proId: "+proId);
		projectService.updateProject(proId,title);
		
		return getProjects();
	}
	
	@RequestMapping(value = "/deleteProject/{proId}", method = RequestMethod.DELETE)
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable long proId) {
        projectService.deleteProject(proId);
        
        return getProjects();
    }
	
}
