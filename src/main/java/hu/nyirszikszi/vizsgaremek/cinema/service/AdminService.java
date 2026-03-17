package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateMovieRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateTheaterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Seat;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateTheaterException;
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

    public void deleteTheaterById(Long id){
        if (!theaterRepository.existsById(id)){
            throw new TheaterNotFoundException();
        }

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

    public void deleteMovieById(Long id){
        if (!movieRepository.existsById(id)){
            throw new MovieNotFoundException();
        }
    }



}
