package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.*;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/admin")
public class AdminController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final UserService userService;
    private final ShowtimeService showtimeService;


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id){
        userService.deleteUserById(id);
    }


    @PostMapping("/create/theater")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public TheaterResponse createTheater(@RequestBody @Valid CreateTheaterRequest request){
        return theaterService.createTheater(request);
    }

    @DeleteMapping("/delete/theater/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTheaterById(@PathVariable Long id){
        theaterService.deleteTheaterById(id);
    }

    @PostMapping("/create/movie")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public MovieResponse createMovie(@RequestBody @Valid CreateMovieRequest request){
        return movieService.createMovie(request);
    }

    @DeleteMapping("/delete/movie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public void deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }

    @PostMapping("/create/showtime")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ShowtimeResponse createShowtime(@RequestBody @Valid CreateShowtimeRequest request){
        return showtimeService.CreateShowtime(request);
    }

    @DeleteMapping("/delete/showtime/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public void deleteShowtimeById(@PathVariable Long id){
        showtimeService.deleteShowtimeById(id);
    }









}
