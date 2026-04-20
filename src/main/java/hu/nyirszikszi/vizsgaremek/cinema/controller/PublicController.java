package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ShowtimeResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.MediaStorageService;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final MediaStorageService mediaStorageService;

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

    @GetMapping("/media/poster/{fileName:.+}")
    public ResponseEntity<Resource> getPoster(@PathVariable String fileName) {
        Resource resource = mediaStorageService.loadPoster(fileName);
        String contentType = mediaStorageService.probeContentType(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/media/trailer/{fileName:.+}")
    public ResponseEntity<Resource> getTrailer(@PathVariable String fileName) {
        Resource resource = mediaStorageService.loadTrailer(fileName);
        String contentType = mediaStorageService.probeContentType(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }




}
