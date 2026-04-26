package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.PublicController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ShowtimeResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.service.MediaStorageService;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PublicControllerMockMvcTest {

    private MockMvc mockMvc;
    private MovieService movieService;
    private TheaterService theaterService;
    private ShowtimeService showtimeService;
    private MediaStorageService mediaStorageService;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class);
        theaterService = mock(TheaterService.class);
        showtimeService = mock(ShowtimeService.class);
        mediaStorageService = mock(MediaStorageService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PublicController(movieService, theaterService, showtimeService, mediaStorageService)).build();
    }

    @Test
    void listMovies_shouldReturnList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of(new MovieResponse(1L, "Dune Part Two", 166, "blank", "blank")));

        mockMvc.perform(get("/cinema/public/list/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Dune Part Two"));
    }

    @Test
    void listTheaters_shouldReturnList() throws Exception {
        when(theaterService.getAllTheaters()).thenReturn(List.of(new TheaterResponse(1L, "Small Hall", TheaterSize.SMALL)));

        mockMvc.perform(get("/cinema/public/list/theaters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Small Hall"));
    }

    @Test
    void listShowtimes_shouldReturnList() throws Exception {
        when(showtimeService.getAllShowtimes()).thenReturn(List.of(
                new ShowtimeResponse(1L, 1L, "Dune Part Two", 1L, "Small Hall", LocalDateTime.parse("2026-05-01T18:00:00"))));

        mockMvc.perform(get("/cinema/public/list/showtimes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movieTitle").value("Dune Part Two"));
    }

    @Test
    void getPoster_shouldReturnBinary() throws Exception {
        ByteArrayResource resource = new ByteArrayResource("poster".getBytes()) {
            @Override
            public String getFilename() { return "poster.jpg"; }
        };
        when(mediaStorageService.loadPoster("poster.jpg")).thenReturn(resource);
        when(mediaStorageService.probeContentType(resource)).thenReturn(MediaType.IMAGE_JPEG_VALUE);

        mockMvc.perform(get("/cinema/public/media/poster/poster.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    void getTrailer_shouldReturnBinary() throws Exception {
        ByteArrayResource resource = new ByteArrayResource("trailer".getBytes()) {
            @Override
            public String getFilename() { return "trailer.mp4"; }
        };
        when(mediaStorageService.loadTrailer("trailer.mp4")).thenReturn(resource);
        when(mediaStorageService.probeContentType(resource)).thenReturn("video/mp4");

        mockMvc.perform(get("/cinema/public/media/trailer/trailer.mp4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("video/mp4"));
    }
}
