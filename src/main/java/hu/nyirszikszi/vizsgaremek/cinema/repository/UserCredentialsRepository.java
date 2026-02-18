package hu.nyirszikszi.vizsgaremek.cinema.repository;


import hu.nyirszikszi.vizsgaremek.cinema.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials,Integer> {

    Optional<UserCredentials> findByUsername(String username);

    Optional<UserCredentials> findByUser_EmailIgnoreCase(String email);
}
