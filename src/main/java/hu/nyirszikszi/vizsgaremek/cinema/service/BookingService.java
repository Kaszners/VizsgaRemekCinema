package hu.nyirszikszi.vizsgaremek.cinema.service;


import hu.nyirszikszi.vizsgaremek.cinema.dto.BookingResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateBookingRequest;
import hu.nyirszikszi.vizsgaremek.cinema.entity.*;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.exception.InvalidCredentialsException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.SeatAlreadyBookedException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.SeatRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;


    @Transactional
    public void createBooking(CreateBookingRequest request){

        boolean alreadyBooked = bookingRepository.existsBySeat_IdAndShowtime_IdAndBookingStatusIn(
                request.SeatId(),
                request.showtimeId(),
                List.of(BookingStatus.PENDING,BookingStatus.CONFIRMED)
        );

        if(alreadyBooked){
            throw new SeatAlreadyBookedException();
        }

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


    public List<BookingResponse> getCurrentUserBookings(){
        String username = SecurityUtil.getCurrentUsername();

        if (username ==  null){
            throw new InvalidCredentialsException();
        }

        UserCredentials userCredentials = userCredentialsRepository.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        User user = userCredentials.getUser();

        return bookingRepository.findAllByUser_Id(user.getId())
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getBookingStatus(),
                        booking.getTimeOfCreation(),
                        booking.getShowtime().getId(),
                        booking.getSeat().getId()
                ))
                .toList();

    }





}
