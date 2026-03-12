package hu.nyirszikszi.vizsgaremek.cinema.seeder;


import hu.nyirszikszi.vizsgaremek.cinema.entity.Movie;
import hu.nyirszikszi.vizsgaremek.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Override
    public void run(String... args){

        if(movieRepository.count()>0){
            return;
        }

        seedMovies();

    }

    private void seedMovies(){
        Movie movie1 = new Movie();

        movie1.setTitle("Dune Part Two");
        movie1.setDuration(166);
        movie1.setPosterUrl("blank");
        movie1.setTrailerUrl("blank");

        Movie movie2 = new Movie();

        movie2.setTitle("Interstellar");
        movie2.setDuration(169);
        movie2.setPosterUrl("blank");
        movie2.setTrailerUrl("blank");


        Movie movie3 = new Movie();

        movie3.setTitle("The Batman");
        movie3.setDuration(176);
        movie3.setPosterUrl("blank");
        movie3.setTrailerUrl("blank");

        Movie movie4 = new Movie();

        movie4.setTitle("Inception");
        movie4.setDuration(148);
        movie4.setPosterUrl("blank");
        movie4.setTrailerUrl("blank");


        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);


    }


}
