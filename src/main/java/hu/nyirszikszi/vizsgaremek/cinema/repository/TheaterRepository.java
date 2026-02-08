package hu.nyirszikszi.vizsgaremek.cinema.repository;


import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater,Integer> {

    Optional<Theater> findByNameIgnoreCase(String name);

}
