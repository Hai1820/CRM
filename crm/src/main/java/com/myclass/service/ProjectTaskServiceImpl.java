package com.myclass.service;

import com.myclass.entity.Backlog;
import com.myclass.entity.Project;
import com.myclass.entity.ProjectTask;
import com.myclass.exceptions.ProjectNotFoundException;
import com.myclass.repository.BacklogRepository;
import com.myclass.repository.ProjectRepository;
import com.myclass.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService{


    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;


    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

            //        pts to be added to a specific project, project !=null, bl exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);

//        set the backlog to the pt
            projectTask.setBacklog(backlog);
            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
//        add sequecne to projectask
            projectTask.setProjectSequence(projectIdentifier +"-"+ BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
//        update the bl sequence

//        install priority when priority null
            if(projectTask.getPriority() == null || projectTask.getPriority() == 0 ){
                projectTask.setPriority(3);
            }
//        install status when status is null
            if(projectTask.getStatus()== "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);


    }

    @Override
    public List<ProjectTask> findBacklogById(String backlog_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    @Override
    public ProjectTask findByProjectSequence(String backlog_id,String sequence, String username) {
//        make sure we are searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id, username);
//        make sure that our task exist
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null){
            throw  new ProjectNotFoundException("Project task with id:'"+sequence+"' not found");
        }
//        make sure the backlog/ projecdt id in the path correspond to the right project
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("project task '"+sequence+"' not found in project :'"+backlog_id+"'");
        }
        return projectTask;
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedProjectTask;
        return projectTaskRepository.save(projectTask);
    }

    @Override
    public void delete(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }


}
