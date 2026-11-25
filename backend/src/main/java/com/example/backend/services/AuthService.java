package com.example.backend.services;

import com.example.backend.dtos.AuthResponse;
import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.RegisterRequest;
import com.example.backend.dtos.UserResponse;
import com.example.backend.entities.User;
import com.example.backend.exceptions.EmailAlreadyUsedException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import com.example.backend.security.JwtService;
import com.example.backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        String email = normalize(request.email());

        if (userRepository.existsByEmail(email))
            throw new EmailAlreadyUsedException("Email already in use!");

        User user = userMapper.toEntity(request);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyUsedException("Email already in use!");
        }
        return createAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalize(request.email()), request.password()));

        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return createAuthResponse(user);
    }

    private String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private AuthResponse createAuthResponse(User user){
        String token = jwtService.generateToken(user.getId());
        Long expiresIn = jwtService.getExpiration();
        UserResponse userResponse= userMapper.toResponse(user);
        return new AuthResponse(token,JwtService.TOKEN_TYPE,expiresIn,userResponse);
    }
}
