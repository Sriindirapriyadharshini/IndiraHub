package IndiraHub.service;

import IndiraHub.dto.AuthResponse;
import IndiraHub.dto.LoginRequest;
import IndiraHub.dto.RegisterRequest;
import IndiraHub.model.User;
import IndiraHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            return new AuthResponse(false, "Username '" + request.getUsername() + "' is already taken");
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            return new AuthResponse(false, "Email '" + request.getEmail() + "' is already registered");
        }

        String fullName = (request.getFullName() != null && !request.getFullName().trim().isEmpty())
                ? request.getFullName()
                : request.getUsername();

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                fullName,
                "CUSTOMER"
        );

        User saved = userRepository.save(user);

        return new AuthResponse(
                true,
                "Registration successful! Welcome to IndiraHub.",
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getFullName(),
                saved.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.getIdentifier().trim();
        Optional<User> userOpt = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(identifier, identifier);

        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "User not found with provided username/email");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Invalid password");
        }

        return new AuthResponse(
                true,
                "Login successful!",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }
}
