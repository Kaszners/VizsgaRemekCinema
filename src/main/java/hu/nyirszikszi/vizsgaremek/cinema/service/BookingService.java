package hu.nyirszikszi.vizsgaremek.cinema.service;


import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateBookingRequest;
import hu.nyirszikszi.vizsgaremek.cinema.entity.*;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.exception.InvalidCredentialsException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.SeatRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;


    public BookingService(BookingRepository bookingRepository, UserCredentialsRepository userCredentialsRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public void createBooking(CreateBookingRequest request){

        String username = SecurityUtil.getCurrentUsername();

        if(username == null){
            throw new InvalidCredentialsException();
        }

        UserCredentials credentials = userCredentialsRepository
                .findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        User user = credentials.getUser();

        Showtime showtime = showtimeRepository
                .findById(request.showtimeId())
                .orElseThrow(RuntimeException::new);

        Seat seat = seatRepository
                .findById(request.SeatId())
                .orElseThrow(RuntimeException::new);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setSeat(seat);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfCreation(LocalDateTime.now());

        bookingRepository.save(booking);

    }




}
