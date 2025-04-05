package Controller;

import Repository.UserRepository;
import dto.JwtResponse;
import dto.RegisterRequest;
import security.JwtUtil;
import Entities.User;
import Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public JwtResponse registerUser(@RequestBody RegisterRequest request) {
        return userService.register(request); // calls the register method in UserService
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}

