package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.UserProfileResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/user")
public class UserController {

    private final UserService userService;




    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getMyProfile(){
         return userService.getCurrentUserProfile();
    }

    @DeleteMapping("/delete/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(){
        userService.deleteCurrentUser();
    }


}
