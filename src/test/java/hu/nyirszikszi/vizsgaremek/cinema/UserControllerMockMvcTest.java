package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.UserController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.UserProfileResponse;
import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerMockMvcTest {

    private MockMvc mockMvc;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void me_shouldReturnProfile() throws Exception {
        when(userService.getCurrentUserProfile()).thenReturn(new UserProfileResponse("john@example.com", "John Doe", UserRole.USER));

        mockMvc.perform(get("/cinema/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void deleteMe_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/cinema/user/delete/me"))
                .andExpect(status().isNoContent());

        verify(userService).deleteCurrentUser();
    }

    @Test
    void changeUsername_shouldReturnOk() throws Exception {
        mockMvc.perform(put("/cinema/user/change-username/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"pass\",\"newUsername\":\"john2\"}"))
                .andExpect(status().isOk());

        verify(userService).changeUsername(any());
    }

    @Test
    void changePassword_shouldReturnOk() throws Exception {
        mockMvc.perform(put("/cinema/user/change-password/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"pass\",\"newPassword\":\"pass2\"}"))
                .andExpect(status().isOk());

        verify(userService).changePassword(any());
    }
}
