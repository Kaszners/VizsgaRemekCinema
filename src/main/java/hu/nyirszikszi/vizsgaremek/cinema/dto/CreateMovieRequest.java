package hu.nyirszikszi.vizsgaremek.cinema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {

    @NotBlank
    @NotNull
    private String title;


    @NotNull
    private int duration;

    @URL
    private String posterUrl;

    @URL
    private String trailerUrl;

}
