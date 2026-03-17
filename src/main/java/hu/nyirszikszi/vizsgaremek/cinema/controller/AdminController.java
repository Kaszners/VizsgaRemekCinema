package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.service.AdminService;
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




}
