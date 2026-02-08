package hu.nyirszikszi.vizsgaremek.cinema.repository;


import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findMovieByTitle(String title);

}
