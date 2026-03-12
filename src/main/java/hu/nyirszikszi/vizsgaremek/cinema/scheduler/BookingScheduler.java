package hu.nyirszikszi.vizsgaremek.cinema.scheduler;


import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingScheduler {

    private final BookingRepository bookingRepository;


    public BookingScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    @Scheduled(fixedRate = 60000)
    public void expirePendingBookings(){

        List<Booking> expiredBookings =
                bookingRepository.findAllByBookingStatusAndTimeOfCreationBefore(
                        BookingStatus.PENDING,
                        LocalDateTime.now().minusMinutes(10)
                );

        expiredBookings.forEach(
                booking -> booking.setBookingStatus(BookingStatus.EXPIRED)
        );

        bookingRepository.saveAll(expiredBookings);


    }

}
