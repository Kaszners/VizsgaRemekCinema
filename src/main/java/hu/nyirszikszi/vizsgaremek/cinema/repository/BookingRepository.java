package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findAllByUser_Id(int id);


    boolean existsBySeat_IdAndShowtime_IdAndBookingStatusIn(
            int seat,
            int showtime,
            List<BookingStatus> statuses
    );

    List<Booking> findAllByBookingStatusAndTimeOfCreationBefore(
            BookingStatus status,
            LocalDateTime time
    );


}
