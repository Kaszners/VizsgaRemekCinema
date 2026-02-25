package hu.nyirszikszi.vizsgaremek.cinema.dto;


import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private int id;

    private String email;

    private String fullName;

    private UserRole role;





}
