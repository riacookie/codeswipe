package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/{userId}")
    public User updateProfile(@PathVariable Long userId, @RequestBody User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
            if (updatedUser.getExperienceLevel() != null) user.setExperienceLevel(updatedUser.getExperienceLevel());
            if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
            if (updatedUser.getTelephone() != null) user.setTelephone(updatedUser.getTelephone());
            return userRepository.save(user);
        }).orElseThrow();
    }

    @GetMapping("/{userId}")
    public User getProfile(@PathVariable Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

//    Session checking API
    @GetMapping("/me")
    public ResponseEntity<?> getMe(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(user);
    }
}