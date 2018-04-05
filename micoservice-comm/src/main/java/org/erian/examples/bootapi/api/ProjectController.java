package org.erian.examples.bootapi.api;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.erian.examples.bootapi.domain.*;
import org.erian.examples.bootapi.service.*;
import org.erian.modules.constants.MediaTypes;
import org.javasimon.aop.Monitored;

@CrossOrigin
@RestController
public class ProjectController {

	
	public static final String PROJECT_PATH = "/api/project/";
	
	private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/api/projects",method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public List<Project> listAllProject() {
		List<Project> projects = projectService.findAll();
		return projects;
	}

	@RequestMapping(value = "/api/projects/{id}", method=RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@Monitored
	public Project listOneProject(@PathVariable("id") Integer id) {
		Project project = projectService.findOne(id);
		return project;
	}

//	Content-Type: application/json;charset=UTF-8
	@RequestMapping(value = "/api/projects", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public Project createProject(@RequestBody Project project, UriComponentsBuilder uriBuilder) {
		
		project = projectService.saveProject(project);
			
		return project;
	}
	
	@RequestMapping(value = "/api/projects/{id}", method = RequestMethod.DELETE)
	@Monitored
	public void deleteProject(@NotNull @PathVariable("id") Integer id) {
		projectService.deleteProject(id);
	}

	@RequestMapping(value = "/api/projects", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
	@Monitored
	public Project modifyProject(@RequestBody Project project,
			@RequestParam(value = "token", required = false) String token) {
		
		return projectService.modifyProject(project);
	}
}
