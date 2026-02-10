package hu.nyirszikszi.vizsgaremek.cinema.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotNull
    private String usernameOrEmail;

    @NotNull
    @Size(min = 8)
    private String password;

}
