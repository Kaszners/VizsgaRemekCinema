package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateMovieRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateTheaterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.MovieResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.TheaterResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/admin")
public class AdminController {

    private final AdminService adminService;


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id){
        adminService.deleteUserById(id);
    }


    @PostMapping("/create/theater")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("HasRole('ADMIN')")
    public TheaterResponse createTheater(@RequestBody @Valid CreateTheaterRequest request){
        return adminService.createTheater(request);
    }

    @DeleteMapping("/delete/theater/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTheaterById(@PathVariable Long id){
        adminService.deleteTheaterById(id);
    }

    @PostMapping("/create/movie")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("HasRole('ADMIN')")
    public MovieResponse createMovie(@RequestBody @Valid CreateMovieRequest request){
        return adminService.createMovie(request);
    }

    @DeleteMapping("/delete/movie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMoiveById(@PathVariable Long id){
        adminService.deleteMovieById(id);
    }






}
