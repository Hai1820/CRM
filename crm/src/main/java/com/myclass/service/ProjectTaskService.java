package com.myclass.service;

import com.myclass.entity.Project;
import com.myclass.entity.ProjectTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectTaskService {
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

    List<ProjectTask> findBacklogById(String backlog_id, String username);
    public ProjectTask findByProjectSequence(String backlog_id,String sequence, String username);
    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id, String username);
    public void delete( String backlog_id, String pt_id, String username);
}
