package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Seat;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.repository.SeatRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;


    public void createTheater(String name, TheaterSize size){
        Theater theater = new Theater();
        theater.setName(name);
        theater.setSize(size);

        theaterRepository.save(theater);

        generateSeats(theater);

    }



    public void generateSeats(Theater theater){
        int rows = theater.getSize().getRows();
        int colums = theater.getSize().getColumns();

        for (int row =1; row <= rows; row++){
            for (int seatNumber = 1; seatNumber <= colums;seatNumber++){
                Seat seat = new Seat();
                seat.setRowNumber(row);
                seat.setSeatNumber(seatNumber);
                seat.setTheater(theater);
                seatRepository.save(seat);
            }
        }


    }



}
