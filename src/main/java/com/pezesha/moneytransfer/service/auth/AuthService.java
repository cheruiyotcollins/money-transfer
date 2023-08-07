package com.pezesha.moneytransfer.service.auth;


import com.pezesha.moneytransfer.dto.AddRoleRequest;
import com.pezesha.moneytransfer.dto.LoginDto;
import com.pezesha.moneytransfer.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);

    ResponseEntity<?> register(SignUpRequest signUpRequest);

    ResponseEntity<?> addRole(AddRoleRequest addRoleRequest);

    ResponseEntity<?> findUserById(long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> deleteById(long id);


    ResponseEntity<?> addUser(SignUpRequest signUpRequest);

    ResponseEntity<?> getCurrentUser(String name);
}
