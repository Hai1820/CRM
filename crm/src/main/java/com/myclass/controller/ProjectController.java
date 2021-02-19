package com.myclass.controller;

import com.myclass.entity.Project;
import com.myclass.service.MapValidationErrorService;
import com.myclass.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;


    public ProjectController(ProjectService projectService, MapValidationErrorService mapValidationErrorService) {
        this.projectService = projectService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/add-project")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody  Project project,
                                              BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null){
            return errorMap;
        }
        Project projectSave = projectService.saveOrUpdate(project, principal.getName());
        return new ResponseEntity<Project>(projectSave, HttpStatus.CREATED);
    }

    @GetMapping("/find-project/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        Project project = projectService.findProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }


    @GetMapping("/all-project")
    public Iterable<Project> getAll(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/delete-project/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String>("Project with ID:'"+projectId+"' was deleted", HttpStatus.OK);
    }
}
