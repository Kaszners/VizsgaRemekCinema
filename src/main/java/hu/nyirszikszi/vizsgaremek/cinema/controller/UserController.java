package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.UserProfileResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/cinema/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable int id){
        userService.deleteUserById(id);

    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getMyProfile(){
         return userService.getCurrentUserProfile();
    }


}
