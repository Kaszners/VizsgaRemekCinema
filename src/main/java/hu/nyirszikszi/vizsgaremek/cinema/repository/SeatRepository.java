package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByTheater_Id(int id);

}
