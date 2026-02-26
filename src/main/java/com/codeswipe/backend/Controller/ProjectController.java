package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.Project;
import com.codeswipe.backend.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    // 1. Get the "Deck" of cards for a specific user
    @GetMapping("/deck/{userId}")
    public List<Project> getProjectDeck(@PathVariable Long userId) {
        return projectRepository.findProjectsNotSwipedByUser(userId);
    }

    // 2. Get all projects (Just for debugging)
    @GetMapping("/all")
    public List<Project> getAll() {
        return projectRepository.findAll();
    }
}