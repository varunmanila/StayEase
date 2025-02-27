package com.example.StayEase.Service;


import com.example.StayEase.Entity.Role;
import com.example.StayEase.Entity.User;
import com.example.StayEase.Repocitory.UserRepository;
import com.example.StayEase.request.AuthRequest;
import com.example.StayEase.request.RegisterRequest;
import com.example.StayEase.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = User.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user,user.getEmail());
        userRepository.save(user);
                return AuthResponse.builder()
            .accessToken(jwtToken)
            .build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        user.setUserName(user.getEmail());
        String jwtToken = jwtService.generateToken(user, user.getEmail());
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
}
}
