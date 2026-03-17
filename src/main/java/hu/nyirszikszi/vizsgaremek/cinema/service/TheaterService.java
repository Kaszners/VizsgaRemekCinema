package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateTheaterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateTheaterException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.TheaterNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final SeatService seatService;


    @Transactional
    public TheaterResponse createTheater(CreateTheaterRequest request){
        if (theaterRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateTheaterException();
        }
        Theater theater = new Theater();
        theater.setName(request.getName());
        theater.setSize(request.getSize());

        Theater saved = theaterRepository.save(theater);

        seatService.generateSeats(saved);

        return new TheaterResponse(
                saved.getId(),
                saved.getName(),
                saved.getSize()
        );
    }

    @Transactional
    public void deleteTheaterById(Long id){
        if (!theaterRepository.existsById(id)){
            throw new TheaterNotFoundException();
        }

        List<Showtime> showtimes = showtimeRepository.findAllByTheater_Id(id);

        for(Showtime showtime : showtimes){
            bookingRepository.deleteAllByShowtime_Id(showtime.getId());
        }

        showtimeRepository.deleteAllByTheater_Id(id);
        seatRepository.deleteAllByTheater_Id(id);
        theaterRepository.deleteById(id);
    }


    public List<TheaterResponse> getAllTheaters(){
        return theaterRepository.findAll()
                .stream()
                .map(theater -> new TheaterResponse(
                        theater.getId(),
                        theater.getName(),
                        theater.getSize()
                ))
                .toList();
    }



}
