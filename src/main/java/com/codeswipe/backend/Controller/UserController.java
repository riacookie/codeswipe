package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Update profile (Skills and Experience)
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
}