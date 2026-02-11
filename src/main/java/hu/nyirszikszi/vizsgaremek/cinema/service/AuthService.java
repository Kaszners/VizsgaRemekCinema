package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.config.SecurityConfig;
import hu.nyirszikszi.vizsgaremek.cinema.dto.AuthResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.LoginRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.RegisterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.entity.User;
import hu.nyirszikszi.vizsgaremek.cinema.entity.UserCredentials;
import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateEmailException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateUsernameException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.InvalidCredentialsException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserRepository;
import hu.nyirszikszi.vizsgaremek.cinema.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserCredentialsRepository userCredentialsRepository, SecurityConfig securityConfig, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {
        userRepository.findByEmailIgnoreCase(request.getEmail()).ifPresent(user -> {
            throw new DuplicateEmailException(request.getEmail());
        });

        userCredentialsRepository.findByUsername(request.getUsername()).ifPresent(userCredentials -> {
            throw new DuplicateUsernameException(request.getUsername());
        });


        User user = new User();

        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);

        UserCredentials userCredentials = new UserCredentials();

        userCredentials.setUsername(request.getUsername());
        userCredentials.setPassword(passwordEncoder.encode(request.getPassword()));
        userCredentials.setUser(savedUser);

        userCredentialsRepository.save(userCredentials);

    }

    public AuthResponse login(LoginRequest request){

        Optional<UserCredentials> credentials;

        if (request.getUsernameOrEmail().contains("@")){
            credentials = userCredentialsRepository.findByUser_EmailIgnoreCase(request.getUsernameOrEmail());
        }else {
            credentials = userCredentialsRepository.findByUsername(request.getUsernameOrEmail());
        }

        UserCredentials userCredentials = credentials
                .orElseThrow(() -> new InvalidCredentialsException());


        if (!passwordEncoder.matches(request.getPassword(), userCredentials.getPassword())){
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generateToken(
                userCredentials.getUsername(),
                userCredentials.getUser().getRole().name()
        );

        return new AuthResponse(token);

    }


}
