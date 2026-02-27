package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.Project;
import com.codeswipe.backend.Entity.Swipe;
import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.ProjectRepository;
import com.codeswipe.backend.Repository.SwipeRepository;
import com.codeswipe.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping
    public String saveSwipe(@RequestParam Long userId, @RequestParam Long projectId, @RequestParam String type) {
        User user = userRepository.findById(userId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();

        Swipe swipe = new Swipe();
        swipe.setUser(user);
        swipe.setProject(project);
        swipe.setSwipeType(type.toUpperCase());
        swipe.setSwipeTime(LocalDateTime.now());

        swipeRepository.save(swipe);
        return "Swipe " + type + " saved!";
    }


    @GetMapping("/liked/{userId}")
    public List<Project> getLikedProjects(@PathVariable Long userId) {
        return swipeRepository.findByUserUserIdAndSwipeType(userId, "LIKE")
                .stream()
                .map(Swipe::getProject)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/unlike")
    public String unlike(@RequestParam Long userId, @RequestParam Long projectId) {
        // Logic: Find the swipe record for this user/project and delete it
        // For hackathon speed, you can use a native query in SwipeRepository
        swipeRepository.deleteByUserIdAndProjectId(userId, projectId);
        return "Unliked";
    }

    @DeleteMapping("/reset-skips/{userId}")
    public ResponseEntity<String> resetSkips(@PathVariable Long userId) {
        swipeRepository.deleteSkipsByUserId(userId);
        return ResponseEntity.ok("Skipped projects are back in the deck!");
    }
}