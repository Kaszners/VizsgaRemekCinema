package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.*;
import hu.nyirszikszi.vizsgaremek.cinema.service.MediaStorageService;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/admin")
public class AdminController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final UserService userService;
    private final ShowtimeService showtimeService;
    private final MediaStorageService mediaStorageService;


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

    @PostMapping("/upload/movie/{movieId}/poster")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public MovieResponse uploadMoviePoster(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        String fileName = mediaStorageService.storePoster(file, movieId);
        return movieService.updatePosterUrl(movieId, "/cinema/public/media/poster/" + fileName);
    }

    @PostMapping("/upload/movie/{movieId}/trailer")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public MovieResponse uploadMovieTrailer(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        String fileName = mediaStorageService.storeTrailer(file, movieId);
        return movieService.updateTrailerUrl(movieId, "/cinema/public/media/trailer/" + fileName);
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
