package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.AdminController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ShowtimeResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.service.MediaStorageService;
import hu.nyirszikszi.vizsgaremek.cinema.service.MovieService;
import hu.nyirszikszi.vizsgaremek.cinema.service.ShowtimeService;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerMockMvcTest {

    private MockMvc mockMvc;
    private MovieService movieService;
    private TheaterService theaterService;
    private UserService userService;
    private ShowtimeService showtimeService;
    private MediaStorageService mediaStorageService;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class);
        theaterService = mock(TheaterService.class);
        userService = mock(UserService.class);
        showtimeService = mock(ShowtimeService.class);
        mediaStorageService = mock(MediaStorageService.class);

        AdminController controller = new AdminController(movieService, theaterService, userService, showtimeService, mediaStorageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/cinema/admin/delete/11"))
                .andExpect(status().isNoContent());
        verify(userService).deleteUserById(11L);
    }

    @Test
    void createTheater_shouldReturnCreated() throws Exception {
        when(theaterService.createTheater(any())).thenReturn(new TheaterResponse(1L, "Small Hall", TheaterSize.SMALL));

        mockMvc.perform(post("/cinema/admin/create/theater")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Small Hall\",\"size\":\"SMALL\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Small Hall"));
    }

    @Test
    void deleteTheater_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/cinema/admin/delete/theater/21"))
                .andExpect(status().isNoContent());
        verify(theaterService).deleteTheaterById(21L);
    }

    @Test
    void createMovie_shouldReturnCreated() throws Exception {
        when(movieService.createMovie(any())).thenReturn(new MovieResponse(1L, "Dune Part Two", 166, "blank", "blank"));

        mockMvc.perform(post("/cinema/admin/create/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Dune Part Two\",\"duration\":166,\"posterUrl\":\"https://example.com/poster.jpg\",\"trailerUrl\":\"https://example.com/trailer.mp4\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Dune Part Two"));
    }

    @Test
    void deleteMovie_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/cinema/admin/delete/movie/31"))
                .andExpect(status().isNoContent());
        verify(movieService).deleteMovieById(31L);
    }

    @Test
    void uploadPoster_shouldReturnUpdatedMovie() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "poster.jpg", "image/jpeg", "x".getBytes());
        when(mediaStorageService.storePoster(any(), eq(1L))).thenReturn("poster.jpg");
        when(movieService.updatePosterUrl(1L, "/cinema/public/media/poster/poster.jpg"))
                .thenReturn(new MovieResponse(1L, "Dune Part Two", 166, "/cinema/public/media/poster/poster.jpg", "blank"));

        mockMvc.perform(multipart("/cinema/admin/upload/movie/1/poster").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posterUrl").value("/cinema/public/media/poster/poster.jpg"));
    }

    @Test
    void uploadTrailer_shouldReturnUpdatedMovie() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "trailer.mp4", "video/mp4", "x".getBytes());
        when(mediaStorageService.storeTrailer(any(), eq(1L))).thenReturn("trailer.mp4");
        when(movieService.updateTrailerUrl(1L, "/cinema/public/media/trailer/trailer.mp4"))
                .thenReturn(new MovieResponse(1L, "Dune Part Two", 166, "blank", "/cinema/public/media/trailer/trailer.mp4"));

        mockMvc.perform(multipart("/cinema/admin/upload/movie/1/trailer").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trailerUrl").value("/cinema/public/media/trailer/trailer.mp4"));
    }

    @Test
    void createShowtime_shouldReturnCreated() throws Exception {
        when(showtimeService.CreateShowtime(any()))
                .thenReturn(new ShowtimeResponse(1L, 1L, "Dune Part Two", 1L, "Small Hall", LocalDateTime.parse("2026-05-01T18:00:00")));

        mockMvc.perform(post("/cinema/admin/create/showtime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"movieId\":1,\"theaterId\":1,\"showStartTime\":\"2026-05-01T18:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieTitle").value("Dune Part Two"));
    }

    @Test
    void deleteShowtime_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/cinema/admin/delete/showtime/41"))
                .andExpect(status().isNoContent());
        verify(showtimeService).deleteShowtimeById(41L);
    }
}
