package aprendendo.api.books;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;

import aprendendo.api.books.auth.TokenService;
import aprendendo.api.books.model.User;
import aprendendo.api.books.model.DTO.UserDTO;
import aprendendo.api.books.repository.UserRepository;
import aprendendo.api.books.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserUnitTests {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository, authenticationManager, tokenService);

        user = new User(1L,"Test Name","testEmail@email.com","123");

        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    public void createUserTest() {
        
        User newUser = new User(1L,"Test Name","testEmail@email.com","123");

        UserDTO userDTO = userService.createUser(newUser);

        Assertions.assertEquals(newUser.getEmail(),userDTO.getEmail());
        Assertions.assertEquals(newUser.getId(),userDTO.getId());
    }

    @Test
    public void createUserExceptionTest() {

        when(userRepository.findByEmail("testEmail@email.com")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        Assertions.assertEquals("Email ja está em uso",exception.getMessage());
    }
}
