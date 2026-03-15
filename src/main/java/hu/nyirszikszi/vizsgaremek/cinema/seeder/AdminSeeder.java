package hu.nyirszikszi.vizsgaremek.cinema.seeder;

import hu.nyirszikszi.vizsgaremek.cinema.entity.User;
import hu.nyirszikszi.vizsgaremek.cinema.entity.UserCredentials;
import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {


        if (userRepository.findByRole(UserRole.ADMIN).isPresent()){
            return;
        }
        seedAdmin();
    }

    @Value("${app.default-admin.username}")
    private String adminUsername;

    @Value("${app.default-admin.password}")
    private String adminPassword;

    @Value("${app.default-admin.email}")
    private String adminEmail;

    private void seedAdmin(){

        User user = new User();

        user.setEmail(adminEmail);
        user.setFullName("admin");
        user.setRole(UserRole.ADMIN);

        User savedAdmin = userRepository.save(user);

        UserCredentials userCredentials = new UserCredentials();

        userCredentials.setUsername(adminUsername);
        userCredentials.setPassword(passwordEncoder.encode(adminPassword));
        userCredentials.setUser(savedAdmin);

        userCredentialsRepository.save(userCredentials);
    }

}
