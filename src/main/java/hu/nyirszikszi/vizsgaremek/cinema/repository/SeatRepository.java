package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTheater_Id(Long id);

    List<Seat> deleteAllByTheater_Id(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s frome Seat s WHERE s.id = :id ")
    Optional<Seat> findByIdWithLock(Long id);

}
