package hu.nyirszikszi.vizsgaremek.cinema;

import hu.nyirszikszi.vizsgaremek.cinema.controller.AuthController;
import hu.nyirszikszi.vizsgaremek.cinema.dto.AuthResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerMockMvcTest {

    private MockMvc mockMvc;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService)).build();
    }

    @Test
    void register_shouldReturnCreated() throws Exception {
        String body = "{" +
                "\"username\":\"john\"," +
                "\"password\":\"password123\"," +
                "\"email\":\"john@example.com\"," +
                "\"fullName\":\"John Doe\"}";

        mockMvc.perform(post("/cinema/authentication/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        verify(authService).register(any());
    }

    @Test
    void login_shouldReturnToken() throws Exception {
        when(authService.login(any())).thenReturn(new AuthResponse("token-123"));

        mockMvc.perform(post("/cinema/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOrEmail\":\"john\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-123"));
    }
}
