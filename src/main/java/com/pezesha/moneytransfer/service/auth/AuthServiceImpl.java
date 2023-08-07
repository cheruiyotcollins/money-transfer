package com.pezesha.moneytransfer.service.auth;


import com.pezesha.moneytransfer.dto.*;
import com.pezesha.moneytransfer.model.Role;
import com.pezesha.moneytransfer.model.User;
import com.pezesha.moneytransfer.repository.RoleRepository;
import com.pezesha.moneytransfer.repository.UserRepository;
import com.pezesha.moneytransfer.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    ResponseDto responseDto;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        responseDto = new ResponseDto();
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Email Address already in use!");
            return new ResponseEntity(responseDto, responseDto.getStatus());
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Username is already taken!");
            return new ResponseEntity(responseDto, responseDto.getStatus());
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        if (roleRepository.findByName("ADMIN").isPresent()) {
            Role userRole = roleRepository.findByName("ADMIN").get();
            user.setRoles(Collections.singleton(userRole));
        }
        userRepository.save(user);
        responseDto.setStatus(HttpStatus.ACCEPTED);
        responseDto.setDescription("User registered successfully");
        return new ResponseEntity(responseDto, responseDto.getStatus());
    }

    @Override
    public ResponseEntity<?> addRole(AddRoleRequest addRoleRequest) {
        Role role = new Role();
        role.setName(addRoleRequest.getName());
        roleRepository.save(role);
        return null;
    }

    @Override
    public ResponseEntity<?> findUserById(long id) {
        responseDto = new ResponseDto();
        try {

            return new ResponseEntity<>(userRepository.findById(id).get(), HttpStatus.OK);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("User Not Found");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        responseDto = new ResponseDto();
        try {

            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No User Found");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(long id) {
        responseDto = new ResponseDto();
        try {
            responseDto.setStatus(HttpStatus.ACCEPTED);
            responseDto.setDescription("User deleted successfully");
            userRepository.deleteById(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("User with that id not found");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> addUser(SignUpRequest signUpRequest) {
        responseDto = new ResponseDto();
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Email Address already in use!");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Username is already taken!");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        if (roleRepository.existsById(signUpRequest.getRoleId())) {
            Role userRole = roleRepository.findById(signUpRequest.getRoleId()).get();
            user.setRoles(Collections.singleton(userRole));
        }
        userRepository.save(user);
        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("User Added successfully");
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    @Override
    public ResponseEntity<?> getCurrentUser(String email) {

        CurrentUserDto currentUserDto = new CurrentUserDto();
        User user = userRepository.findByEmail(email).get();
        currentUserDto.setName(user.getUsername());
        currentUserDto.setEmail(user.getEmail());
        currentUserDto.setRole(user.getRoles().iterator().next().getId());
        System.out.println(currentUserDto.getRole());
        return new ResponseEntity<>(currentUserDto, HttpStatus.OK);
    }


}
