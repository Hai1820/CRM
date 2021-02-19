package com.myclass.service;

import com.myclass.entity.Project;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    Project saveOrUpdate(Project project, String username);
    Project findProjectByIdentifier(String projectId, String username);
    Iterable<Project> findAllProjects(String username);
    void deleteProjectByIdentifier(String projectId, String username);
}
