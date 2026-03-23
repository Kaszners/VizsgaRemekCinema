package hu.nyirszikszi.vizsgaremek.cinema.service;


import hu.nyirszikszi.vizsgaremek.cinema.dto.BookingResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateBookingRequest;
import hu.nyirszikszi.vizsgaremek.cinema.entity.*;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.exception.*;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.SeatRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final EmailService emailService;


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

        Showtime showtime = showtimeRepository.findById(request.showtimeId())
                .orElseThrow(RuntimeException::new);

        String confirmationToken = UUID.randomUUID().toString();

        List<Booking> bookings = new ArrayList<>();

        for (Long seatId : request.seatIds()){
            Seat seat = seatRepository.findByIdWithLock(seatId)
                    .orElseThrow(SeatAlreadyBookedException::new);

            boolean alreadyBooked = bookingRepository
                    .existsBySeat_IdAndShowtime_IdAndBookingStatusIn(
                            seatId,
                            request.showtimeId(),
                            List.of(BookingStatus.PENDING,BookingStatus.CONFIRMED)
            );
            if(alreadyBooked){
                throw new SeatAlreadyBookedException();
            }
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setShowtime(showtime);
            booking.setSeat(seat);
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setTimeOfCreation(LocalDateTime.now());
            booking.setConfirmationToken(confirmationToken);

            bookings.add(booking);


        }

        bookingRepository.saveAll(bookings);

        emailService.sendBookingConfirmation(user.getEmail(),confirmationToken,bookings);

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


    public void confirmBooking(String token){

        List<Booking> bookings = bookingRepository.findAllByConfirmationToken(token);

        if (bookings.isEmpty()){
            throw new InvalidConfirmationTokenException();
        }
        for (Booking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.CONFIRMED){
                continue;
            }
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        }


    }

    public void cancelBooking(Long bookingId){
        String username = SecurityUtil.getCurrentUsername();

        UserCredentials credentials = userCredentialsRepository
                .findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        if (!booking.getUser().getId().equals(credentials.getUser().getId())){
            throw new AccessDeniedException("Not current users booking");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw new IllegalStateException("Already cancelled");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);


    }







}
