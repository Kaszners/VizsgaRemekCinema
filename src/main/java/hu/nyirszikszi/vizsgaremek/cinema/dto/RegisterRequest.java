package hu.nyirszikszi.vizsgaremek.cinema.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {


    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String fullName;


}
