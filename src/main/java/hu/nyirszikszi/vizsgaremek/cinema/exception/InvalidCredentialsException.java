package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("A megadott email vagy felhasználónév nem létezik vagy helytelen jelszót adott meg");
    }
}
