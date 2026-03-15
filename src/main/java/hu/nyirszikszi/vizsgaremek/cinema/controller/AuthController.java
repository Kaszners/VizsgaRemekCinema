package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.AuthResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.LoginRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.RegisterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/authentication")
public class AuthController {

    private final AuthService authService;



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
