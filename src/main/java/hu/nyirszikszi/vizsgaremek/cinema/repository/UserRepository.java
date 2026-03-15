package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.User;
import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByRole(UserRole role);

    //optional maybe later for admin search
    //List<User> findByFullName(String fullName);


}
