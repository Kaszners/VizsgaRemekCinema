package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Ezzel az email-al már regisztráltak felhasználót ");
    }
}
