package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.dto.*;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateTheaterException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.MovieNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.TheaterNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.UserNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;
    private final SeatService seatService;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException:: new );

        userCredentialsRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }


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


    public MovieResponse createMovie(CreateMovieRequest request){
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setTrailerUrl(request.getTrailerUrl());

        Movie saved = movieRepository.save(movie);

        return new MovieResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDuration(),
                saved.getPosterUrl(),
                saved.getTrailerUrl()
        );
    }

    @Transactional
    public void deleteMovieById(Long id){
        if (!movieRepository.existsById(id)){
            throw new MovieNotFoundException();
        }
        List<Showtime> showtimes = showtimeRepository.findAllByMovie_Id(id);

        for (Showtime showtime : showtimes) {
            bookingRepository.deleteAllByShowtime_Id(id);
        }
        showtimeRepository.deleteAllByMovie_Id(id);
        movieRepository.deleteById(id);
    }

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





}
