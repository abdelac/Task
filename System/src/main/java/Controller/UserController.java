package Controller;

import Services.JwtService;
import Services.UserService;
import dto.LoginRequest;
import dto.RegisterRequest;
import dto.UpdateRequest;
import dto.UserResponse;
import jakarta.servlet.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserDetails(HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(userService.getUserDetails(email));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateRequest request, HttpServletRequest httpRequest) {
        String token = jwtService.extractTokenFromRequest(httpRequest);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(userService.updateUser(email, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(HttpServletRequest httpRequest) {
        String token = jwtService.extractTokenFromRequest(httpRequest);
        String email = jwtService.extractUsername(token);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
