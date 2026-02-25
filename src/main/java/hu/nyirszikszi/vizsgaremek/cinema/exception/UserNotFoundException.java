package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {super("Nem létezik ilyen felhasznalo :p");
    }
}
