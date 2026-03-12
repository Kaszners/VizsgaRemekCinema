package hu.nyirszikszi.vizsgaremek.cinema.seeder;


import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TheaterService theaterService;
    private final TheaterRepository theaterRepository;

    @Override
    public void run(String... args){

        if(theaterRepository.count()>0){
            return;
        }

        theaterService.createTheater("Small Theater 1", TheaterSize.SMALL);
        theaterService.createTheater("Small Theater 2", TheaterSize.SMALL);

        theaterService.createTheater("Large Theater 1",TheaterSize.LARGE);
        theaterService.createTheater("Large Theater 2",TheaterSize.LARGE);
    }


}
