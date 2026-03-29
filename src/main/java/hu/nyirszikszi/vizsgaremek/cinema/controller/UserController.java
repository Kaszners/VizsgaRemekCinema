package hu.nyirszikszi.vizsgaremek.cinema.controller;


import hu.nyirszikszi.vizsgaremek.cinema.dto.ChangePasswordRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ChangeUsernameRequest;
import hu.nyirszikszi.vizsgaremek.cinema.dto.UserProfileResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.UserService;
import jakarta.validation.Valid;
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

    @PutMapping("/change-username/me")
    @PreAuthorize("hasAnyRole('USER','STAFF','ADMIN')")
    public void changeCurrentUsername(@RequestBody @Valid ChangeUsernameRequest request){
        userService.changeUsername(request);
    }

    @PutMapping("/change-password/me")
    @PreAuthorize("hasAnyRole('USER','STAFF','ADMIN')")
    public void changeCurrentPassword(@RequestBody @Valid ChangePasswordRequest request){
        userService.changePassword(request);
    }


}
