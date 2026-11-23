package com.example.backend.security;

import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        return new UserDetailsImpl(user);
    }

    public UserDetails loadUserById(Long id) throws UserNotFoundException {
        User user= userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found!"));
        return new UserDetailsImpl(user);
    }

}
