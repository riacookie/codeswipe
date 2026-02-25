package com.codeswipe.backend.Controller;

import com.codeswipe.backend.Entity.User;
import com.codeswipe.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/google-login")
    public ResponseEntity<User> googleLogin(@RequestBody User googleUser) {
        Optional<User> userOptional = userRepository.findByEmail(googleUser.getEmail());

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            User savedUser = userRepository.save(googleUser);
            return ResponseEntity.ok(savedUser);
        }
    }
}