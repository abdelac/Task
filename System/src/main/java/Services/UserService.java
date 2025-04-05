package Services;
import dto.*;
import Entities.User;
import Repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public JwtResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Generate JWT using email (or another field like name) instead of the whole User object
        String token = jwtService.generateToken(user.getEmail());  // Pass the user's email instead of the full User object
        return new JwtResponse(token); // Return the generated JWT
    }


    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(String.valueOf(user));
        return new JwtResponse(token);
    }

    public UserResponse getUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public UserResponse updateUser(String email, UpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }
}

