package hu.nyirszikszi.vizsgaremek.cinema.service;


import hu.nyirszikszi.vizsgaremek.cinema.dto.SeatAvailabilityResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Seat;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.exception.ShowtimeNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.SeatRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;


    public void generateSeats(Theater theater){
        List<Seat> seats = new ArrayList<>();

        int rows = theater.getSize().getRows();
        int cols = theater.getSize().getColumns();



        for(int r = 1; r <= rows; r++){
            for(int c = 1; c <= cols; c++){
                Seat seat = new Seat();
                seat.setRowNumber(r);
                seat.setSeatNumber(c);
                seat.setTheater(theater);

                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);

    }

    public List<SeatAvailabilityResponse> getSeatAvailability(Long showtimeId){
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(ShowtimeNotFoundException::new);

        Theater theater = showtime.getTheater();

        List<Seat> seats = seatRepository.findByTheater_Id(theater.getId());

        List<Booking> bookings = bookingRepository.findByShowtime_Id(showtimeId);

        Map<Long, BookingStatus> seatStatusMap =
                bookings.stream()
                        .collect(Collectors.toMap(
                                booking -> booking.getSeat().getId(),
                                Booking::getBookingStatus
                        ));

        return seats.stream()
                .map(seat -> {
                    BookingStatus bookingStatus = seatStatusMap.get(seat.getId());

                    String status = "AVAILABLE";

                    if (bookingStatus == BookingStatus.PENDING){
                        status = "LOCKED";
                    }
                    if (bookingStatus == BookingStatus.CONFIRMED){
                        status = "BOOKED";
                    }

                    return new SeatAvailabilityResponse(
                            seat.getId(),
                            seat.getRowNumber(),
                            seat.getSeatNumber(),
                            status
                    );
                })
                .toList();

    }



}
