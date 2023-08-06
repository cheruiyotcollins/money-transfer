package com.pezesha.moneytransfer.controller;




import com.pezesha.moneytransfer.dto.AddRoleRequest;
import com.pezesha.moneytransfer.dto.JWTAuthResponse;
import com.pezesha.moneytransfer.dto.LoginDto;
import com.pezesha.moneytransfer.dto.SignUpRequest;
import com.pezesha.moneytransfer.model.Role;
import com.pezesha.moneytransfer.model.User;
import com.pezesha.moneytransfer.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/auth/")
public class AuthController {
    @Autowired
    AuthService authService;




    public AuthController(AuthService authService) {
        this.authService = authService;
    }


     @Operation(summary = "User sign in/ login")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User Logged In Successfully",
                content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
        @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping(value = {"/login", "/signin"})
     @PreAuthorize("permitAll()")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @Operation(summary = "New User Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping(value = {"/register", "/signup"})
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest){
               return authService.register(signUpRequest);
    }


    @Operation(summary = "Update User Info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Information Updated Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})

    @PutMapping("add")
    public ResponseEntity<?> addOrUpdate(@RequestBody SignUpRequest signUpRequest){
        return authService.addUser(signUpRequest);

    }
    @Operation(summary = "Find User By  his/her Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "User found",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return authService.findUserById(id);

    }
    @Operation(summary = "List All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "returned list of users",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "no user found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("list/all")
    public ResponseEntity<?> findAll(){
        return authService.findAll();

    }
    @Operation(summary = "Delete User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return authService.deleteById(id);

    }
    @Operation(summary = "Create New Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Role.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Role not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping("new/role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest addRoleRequest){
        return authService.addRole(addRoleRequest);
    }
    @Operation(summary = "Find Current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Role.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Role not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("current")
    public ResponseEntity<?> getCurrentUser( Authentication authentication){

        return authService.getCurrentUser(authentication.getName());
    }
}
