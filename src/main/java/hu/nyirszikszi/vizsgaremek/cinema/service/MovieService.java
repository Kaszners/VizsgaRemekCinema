package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateMovieRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.exception.MovieNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.MovieRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final BookingRepository bookingRepository;


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
            bookingRepository.deleteAllByShowtime_Id(showtime.getId());
        }
        showtimeRepository.deleteAllByMovie_Id(id);
        movieRepository.deleteById(id);
    }

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll()
                .stream()
                .map(movie -> new MovieResponse(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDuration(),
                        movie.getPosterUrl(),
                        movie.getTrailerUrl()
                ))
                .toList();
    }

}
