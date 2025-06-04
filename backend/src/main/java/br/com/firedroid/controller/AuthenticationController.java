package br.com.firedroid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.firedroid.DTOs.AuthenticationDTO;
import br.com.firedroid.DTOs.LoginResponseDTO;
import br.com.firedroid.DTOs.RegisterDTO;
import br.com.firedroid.entity.User;
import br.com.firedroid.entity.UserRole;
import br.com.firedroid.repository.UserRepository;
import br.com.firedroid.security.TokenService;
import br.com.firedroid.service.AuthenticationService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    AuthenticationController(AuthenticationService authenticationService) {
    	this.authenticationService = authenticationService;
    }
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TokenService tokenService;

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerData) {
    	if (this.userRepository.findByUsername(registerData.username()) == null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerData.password());
            User newUser = new User(registerData.username(), registerData.email(), encryptedPassword, UserRole.USER);

            this.userRepository.save(newUser);

            ResponseEntity<?> loginResponse = internalLogin(new AuthenticationDTO(registerData.username(), registerData.password()));
            return loginResponse;
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterDTO registerData) {
        if (this.userRepository.findByUsername(registerData.username()) == null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerData.password());
            User newUser = new User(registerData.username(), registerData.email(), encryptedPassword, UserRole.ADMIN);

            this.userRepository.save(newUser);

            ResponseEntity<?> loginResponse = internalLogin(new AuthenticationDTO(registerData.username(), registerData.password()));
            return loginResponse;
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    private ResponseEntity<?> internalLogin(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}