package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.AuthResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.LoginRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.RegisterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinema/authentication")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest request){
        authService.register(request);
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }


}
