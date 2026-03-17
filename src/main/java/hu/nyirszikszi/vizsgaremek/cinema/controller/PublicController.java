package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ShowtimeResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/public")
public class PublicController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final ShowtimeService showtimeService;

    @GetMapping("/list/movies")
    public List<MovieResponse> listAllMovies(){
       return movieService.getAllMovies();
    }

    @GetMapping("/list/theaters")
    public List<TheaterResponse> listAllTheaters(){
        return theaterService.getAllTheaters();
    }

    @GetMapping("/list/showtimes")
    public List<ShowtimeResponse> listAllShowtimes(){
        return showtimeService.getAllShowtimes();
    }




}
