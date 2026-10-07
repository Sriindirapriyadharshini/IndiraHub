package IndiraHub.service;

import IndiraHub.dto.AuthResponse;
import IndiraHub.dto.LoginRequest;
import IndiraHub.dto.RegisterRequest;
import IndiraHub.model.User;
import IndiraHub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User("testuser", "test@example.com", "password123", "Test User", "CUSTOMER");
        sampleUser.setId(5L);
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("new@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(99L);
            return u;
        });

        RegisterRequest req = new RegisterRequest("newuser", "new@example.com", "pass12345", "New User");
        AuthResponse res = userService.register(req);

        assertTrue(res.isSuccess());
        assertEquals("newuser", res.getUsername());
        assertEquals(99L, res.getUserId());
    }

    @Test
    void testRegisterDuplicateEmail() {
        when(userRepository.existsByUsernameIgnoreCase("newuser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("taken@example.com")).thenReturn(true);

        RegisterRequest req = new RegisterRequest("newuser", "taken@example.com", "pass12345", "New User");
        AuthResponse res = userService.register(req);

        assertFalse(res.isSuccess());
        assertTrue(res.getMessage().contains("already registered"));
    }

    @Test
    void testLoginSuccess() {
        when(userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("testuser", "testuser"))
                .thenReturn(Optional.of(sampleUser));

        LoginRequest req = new LoginRequest("testuser", "password123");
        AuthResponse res = userService.login(req);

        assertTrue(res.isSuccess());
        assertEquals("testuser", res.getUsername());
    }

    @Test
    void testLoginInvalidPassword() {
        when(userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("testuser", "testuser"))
                .thenReturn(Optional.of(sampleUser));

        LoginRequest req = new LoginRequest("testuser", "wrongPassword");
        AuthResponse res = userService.login(req);

        assertFalse(res.isSuccess());
        assertEquals("Invalid password", res.getMessage());
    }
}
