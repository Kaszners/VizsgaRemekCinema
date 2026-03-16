package hu.nyirszikszi.vizsgaremek.cinema.seeder;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Showtime;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.repository.MovieRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.ShowtimeRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(3)
public class ShowtimeSeeder implements CommandLineRunner {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;


    @Override
    public void run (String... args){

        if(showtimeRepository.count()>0){
            return;
        }
        seedShowtimes();
    }

    private void seedShowtimes(){
        Movie dune = movieRepository.findById(1L).orElseThrow();
        Movie interstellar = movieRepository.findById(2L).orElseThrow();

        Theater smallTheater = theaterRepository.findById(1L).orElseThrow();
        Theater largeTheater = theaterRepository.findById(3L).orElseThrow();

        Showtime show1 = new Showtime();
        show1.setMovie(dune);
        show1.setTheater(smallTheater);
        show1.setShowStartTime(LocalDateTime.now().plusHours(2));

        Showtime show2 = new Showtime();
        show2.setMovie(dune);
        show2.setTheater(smallTheater);
        show2.setShowStartTime(LocalDateTime.now().plusHours(5));

        Showtime show3 = new Showtime();
        show3.setMovie(interstellar);
        show3.setTheater(largeTheater);
        show3.setShowStartTime(LocalDateTime.now().plusHours(2));

        Showtime show4 = new Showtime();
        show4.setMovie(interstellar);
        show4.setTheater(largeTheater);
        show4.setShowStartTime(LocalDateTime.now().plusHours(5));

        showtimeRepository.save(show1);
        showtimeRepository.save(show2);
        showtimeRepository.save(show3);
        showtimeRepository.save(show4);
    }


}
