package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.Project;
import com.codeswipe.backend.Entity.Swipe;
import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.ProjectRepository;
import com.codeswipe.backend.Repository.SwipeRepository;
import com.codeswipe.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/swipes")
@CrossOrigin(origins = "*")
public class SwipeController {

    @Autowired private SwipeRepository swipeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProjectRepository projectRepository;

    // 1. Submit a swipe
    @PostMapping
    public String saveSwipe(@RequestParam Long userId, @RequestParam Long projectId, @RequestParam String type) {
        User user = userRepository.findById(userId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();

        Swipe swipe = new Swipe();
        swipe.setUser(user);
        swipe.setProject(project);
        swipe.setSwipeType(type.toUpperCase()); // "LIKE" or "SKIP"
        swipe.setSwipeTime(LocalDateTime.now());

        swipeRepository.save(swipe);
        return "Swipe " + type + " saved!";
    }

    // 2. Get the list of "Interested Projects" (Liked list)
    @GetMapping("/liked/{userId}")
    public List<Project> getLikedProjects(@PathVariable Long userId) {
        return swipeRepository.findByUserUserIdAndSwipeType(userId, "LIKE")
                .stream()
                .map(Swipe::getProject)
                .collect(Collectors.toList());
    }
}