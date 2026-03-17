package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findAllByTheater_Id(Long id);

    List<Showtime> deleteAllByTheater_Id(Long id);

    List<Showtime> deleteAllByMovie_Id(Long id);

    List<Showtime> findAllByMovie_Id(Long id);

}
