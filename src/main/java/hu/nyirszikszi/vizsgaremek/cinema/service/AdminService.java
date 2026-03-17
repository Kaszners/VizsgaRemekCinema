package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateTheaterRequest;
import hu.nyirszikszi.vizsgaremek.cinema.entity.Theater;
import hu.nyirszikszi.vizsgaremek.cinema.exception.DuplicateTheaterException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.TheaterNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.UserNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.TheaterRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final TheaterRepository theaterRepository;

    @Transactional
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException:: new );

        userCredentialsRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }



    public void createTheater(CreateTheaterRequest request){
        if (theaterRepository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateTheaterException();
        }
        Theater theater = new Theater();
        theater.setName(request.getName());
        theater.setSize(request.getSize());

        theaterRepository.save(theater);
    }

    public void deleteTheaterById(Long id){
        if (!theaterRepository.existsById(id)){
            throw new TheaterNotFoundException();
        }

        theaterRepository.deleteById(id);

    }


}
