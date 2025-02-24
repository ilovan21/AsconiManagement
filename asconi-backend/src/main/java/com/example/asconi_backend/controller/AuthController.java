package com.example.asconi_backend.controller;

import com.example.asconi_backend.model.Role;
import com.example.asconi_backend.model.RoleType;
import com.example.asconi_backend.model.User;
import com.example.asconi_backend.repository.RoleRepository;
import com.example.asconi_backend.repository.UserRepository;
import com.example.asconi_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return jwt;
    }
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email already in use!";
        }
        Role clientRole = roleRepository.findByName(RoleType.HOSTESS)
                .orElseThrow(() -> new RuntimeException("Error: Role CLIENT not found."));

        User newUser = new User(
                user.getName(),
                user.getEmail(),
                encoder.encode(user.getPassword()),
                user.getTelephone(),
                clientRole
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
