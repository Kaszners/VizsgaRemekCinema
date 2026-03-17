package hu.nyirszikszi.vizsgaremek.cinema.repository;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser_Id(Long id);

    List<Booking> deleteAllByShowtime_Id(Long id);


    boolean existsBySeat_IdAndShowtime_IdAndBookingStatusIn(
            Long seat,
            Long showtime,
            List<BookingStatus> statuses
    );

    List<Booking> findAllByBookingStatusAndTimeOfCreationBefore(
            BookingStatus status,
            LocalDateTime time
    );

    List<Booking> findByShowtime_Id(Long showtimeId);

    Optional<Booking> findByConfirmationToken(String token);

    void deleteAllByUser_Id(Long userId);


}
