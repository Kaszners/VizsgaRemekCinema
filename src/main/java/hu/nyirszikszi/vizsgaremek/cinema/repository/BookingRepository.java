package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findAllByUser_Id(int id);
}
