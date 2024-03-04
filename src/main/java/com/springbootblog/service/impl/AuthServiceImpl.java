package com.springbootblog.service.impl;

import com.springbootblog.entity.Role;
import com.springbootblog.entity.User;
import com.springbootblog.exception.BlogAPIException;
import com.springbootblog.payload.LoginDto;
import com.springbootblog.payload.RegisterDto;
import com.springbootblog.repository.RoleRepository;
import com.springbootblog.repository.UserRepository;
import com.springbootblog.security.JwtTokenProvider;
import com.springbootblog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                            JwtTokenProvider jwtTokenProvider
                    )
    {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateToken(authenticate);// using jwt token

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        //check if user exists in database;
        if(userRepository.existsByUsername(registerDto.getUsername()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"user already exists");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User is successfully registered";
    }
}
