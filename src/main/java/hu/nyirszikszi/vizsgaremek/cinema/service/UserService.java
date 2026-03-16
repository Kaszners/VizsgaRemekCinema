package hu.nyirszikszi.vizsgaremek.cinema.service;



import hu.nyirszikszi.vizsgaremek.cinema.dto.UserProfileResponse;
import hu.nyirszikszi.vizsgaremek.cinema.entity.User;
import hu.nyirszikszi.vizsgaremek.cinema.entity.UserCredentials;
import hu.nyirszikszi.vizsgaremek.cinema.exception.UserNotFoundException;
import hu.nyirszikszi.vizsgaremek.cinema.exception.InvalidCredentialsException;
import hu.nyirszikszi.vizsgaremek.cinema.repository.BookingRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserCredentialsRepository;
import hu.nyirszikszi.vizsgaremek.cinema.repository.UserRepository;
import hu.nyirszikszi.vizsgaremek.cinema.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final BookingRepository bookingRepository;




    @Transactional
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException :: new );

        userCredentialsRepository.deleteByUserId(id);
        userRepository.deleteById(id);
        }



    public UserProfileResponse getCurrentUserProfile(){
        String username = SecurityUtil.getCurrentUsername();

        if (username == null){
            throw new InvalidCredentialsException();
        }

        UserCredentials userCredentials = userCredentialsRepository.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);

        User user = userCredentials.getUser();

        return new UserProfileResponse(
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }

    @Transactional
    public void deleteCurrentUser(){
        String username = SecurityUtil.getCurrentUsername();

        UserCredentials credentials = userCredentialsRepository.findByUsername(username).orElseThrow(InvalidCredentialsException::new);

        User user = credentials.getUser();

        bookingRepository.deleteAllByUser_Id(user.getId());

        userCredentialsRepository.delete(credentials);

        userRepository.delete(user);

    }






    }






