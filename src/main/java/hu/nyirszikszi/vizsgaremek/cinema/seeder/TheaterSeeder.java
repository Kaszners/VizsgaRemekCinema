package hu.nyirszikszi.vizsgaremek.cinema.seeder;


import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateTheaterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import hu.nyirszikszi.vizsgaremek.cinema.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class TheaterSeeder implements CommandLineRunner {

    private final TheaterService theaterService;
    private final TheaterRepository theaterRepository;

    @Override
    public void run(String... args){

        if(theaterRepository.count()>0){
            return;
        }

        theaterService.createTheater(
                new CreateTheaterRequest("Small Theater 1", TheaterSize.SMALL)
        );

        theaterService.createTheater(
                new CreateTheaterRequest("Small Theater 2", TheaterSize.SMALL)
        );

        theaterService.createTheater(
                new CreateTheaterRequest("Large Theater 1", TheaterSize.LARGE)
        );

        theaterService.createTheater(
                new CreateTheaterRequest("Large Theater 2", TheaterSize.LARGE)
        );
    }


}
