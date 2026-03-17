package hu.nyirszikszi.vizsgaremek.cinema.dto;

import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTheaterRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private TheaterSize size;


}
