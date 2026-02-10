package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String username) {
        super( "Már van ilyen elnevezésű felhasználó " + username);
    }
}
