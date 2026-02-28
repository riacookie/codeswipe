package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/google-login")
    public ResponseEntity<User> googleLogin(@RequestBody User googleUser, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(googleUser.getEmail());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = userRepository.save(googleUser);
        }

        // CREATE THE SESSION
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user); // Store user object in session

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy session
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}