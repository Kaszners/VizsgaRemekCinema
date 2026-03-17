package hu.nyirszikszi.vizsgaremek.cinema.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowtimeRequest {

    @NotNull
    private Long movieId;

    @NotNull
    private Long theaterId;

    @NotNull
    LocalDateTime showStartTime;


}
