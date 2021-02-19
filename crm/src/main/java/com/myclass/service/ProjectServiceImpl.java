package com.myclass.service;

import com.myclass.entity.Backlog;
import com.myclass.entity.Project;
import com.myclass.entity.User;
import com.myclass.exceptions.ProjectIdException;
import com.myclass.exceptions.ProjectNotFoundException;
import com.myclass.repository.BacklogRepository;
import com.myclass.repository.ProjectRepository;
import com.myclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;
    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project saveOrUpdate(Project project, String username) {

//        logic

//        project.getId != null
//          find by db id -> null
        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw  new ProjectNotFoundException("Project not found in your account");
            }else  if (existingProject == null) {
                throw  new ProjectNotFoundException("Project with id:'"+project.getProjectIdentifier().toUpperCase()+"' cannot be updated because it does not exist");
            }
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            }
            if(project.getId() !=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project id '"+project.getProjectIdentifier()+"' already exist");
        }

    }

    @Override
    public Project findProjectByIdentifier(String projectId, String username) {
//         only want to return  the  project if  the user looking for it is the owner
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project id '"+projectId+"' does not exists");
        }
        if(!project.getProjectLeader().equals(username)){
            throw  new ProjectNotFoundException("Project not found in your account");
        }

        return  project;
    }

    @Override
    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {

        projectRepository.delete(findProjectByIdentifier(projectId, username));

    }


}
