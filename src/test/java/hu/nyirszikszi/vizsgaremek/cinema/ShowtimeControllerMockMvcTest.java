package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.ShowtimeController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.SeatAvailabilityResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShowtimeControllerMockMvcTest {

    private MockMvc mockMvc;
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        seatService = mock(SeatService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ShowtimeController(seatService)).build();
    }

    @Test
    void seats_shouldReturnAvailability() throws Exception {
        when(seatService.getSeatAvailability(7L)).thenReturn(List.of(
                new SeatAvailabilityResponse(1L, 1, 1, "AVAILABLE")
        ));

        mockMvc.perform(get("/cinema/showtimes/7/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }
}
