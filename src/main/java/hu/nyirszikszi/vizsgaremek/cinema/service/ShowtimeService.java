package hu.nyirszikszi.vizsgaremek.cinema.service;


import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateShowtimeRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ShowtimeResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.exception.MovieNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.ShowtimeNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.TheaterNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.MovieRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public ShowtimeResponse CreateShowtime(CreateShowtimeRequest request){
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(MovieNotFoundException::new);

        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(TheaterNotFoundException::new);

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setTheater(theater);
        showtime.setShowStartTime(request.getShowStartTime());

        Showtime saved = showtimeRepository.save(showtime);

        return new ShowtimeResponse(
                saved.getId(),
                movie.getId(),
                movie.getTitle(),
                theater.getId(),
                theater.getName(),
                saved.getShowStartTime()
        );
    }

    public List<ShowtimeResponse> getAllShowtimes(){
        return showtimeRepository.findAll()
                .stream()
                .map(showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getMovie().getId(),
                        showtime.getMovie().getTitle(),
                        showtime.getTheater().getId(),
                        showtime.getTheater().getName(),
                        showtime.getShowStartTime()
                ))
                .toList();
    }

    @Transactional
    public void deleteShowtimeById(Long id){
        if (!showtimeRepository.existsById(id)){
            throw new ShowtimeNotFoundException();
        }
        bookingRepository.deleteAllByShowtime_Id(id);

        showtimeRepository.deleteById(id);

    }


}
