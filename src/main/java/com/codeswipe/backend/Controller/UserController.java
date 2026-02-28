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
            user.setExperienceLevel(updatedUser.getExperienceLevel());
            user.setRole(updatedUser.getRole());
            user.setTelephone(updatedUser.getTelephone());
            // Add any other fields you want the user to be able to change
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