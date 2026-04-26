package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.BookingController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.BookingResponse;
import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import hu.nyirszikszi.vizsgaremek.cinema.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingControllerMockMvcTest {

    private MockMvc mockMvc;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = mock(BookingService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingController(bookingService)).build();
    }

    @Test
    void createBooking_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/cinema/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"showtimeId\":1,\"seatIds\":[10,11]}"))
                .andExpect(status().isOk());

        verify(bookingService).createBooking(any());
    }

    @Test
    void getMyBookings_shouldReturnList() throws Exception {
        when(bookingService.getCurrentUserBookings()).thenReturn(List.of(
                new BookingResponse(1L, BookingStatus.PENDING, LocalDateTime.parse("2026-04-26T10:00:00"), 2L, 3L)
        ));

        mockMvc.perform(get("/cinema/booking/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1));
    }

    @Test
    void confirm_shouldReturnText() throws Exception {
        mockMvc.perform(get("/cinema/booking/confirm").param("token", "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking confirmed"));

        verify(bookingService).confirmBooking("abc");
    }

    @Test
    void cancel_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/cinema/booking/5/cancel"))
                .andExpect(status().isOk());

        verify(bookingService).cancelBooking(5L);
    }
}
