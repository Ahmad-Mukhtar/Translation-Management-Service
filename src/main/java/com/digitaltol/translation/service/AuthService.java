package com.digitaltol.translation.service;


import com.digitaltol.translation.repository.UserRepository;
import com.digitaltol.translation.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!encoder.matches(password,user.getPassword()))
            throw new IllegalArgumentException("Invalid creds");
        return jwtUtil.generateToken(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
