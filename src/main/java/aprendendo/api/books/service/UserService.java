package aprendendo.api.books.service;

import aprendendo.api.books.auth.TokenService;
import aprendendo.api.books.model.DTO.LoginDTO;
import aprendendo.api.books.model.DTO.TokenDTO;
import aprendendo.api.books.model.DTO.UserDTO;
import aprendendo.api.books.model.User;
import aprendendo.api.books.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public UserService(UserRepository userRepository,AuthenticationManager authenticationManager,TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public UserDTO createUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepository.save(user).toDTO();
        }

        throw new RuntimeException("Email ja está em uso");
    }

    public TokenDTO login(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generate(authentication);
        return TokenDTO.builder().type("Bearer").token(token).build();
    }
}
