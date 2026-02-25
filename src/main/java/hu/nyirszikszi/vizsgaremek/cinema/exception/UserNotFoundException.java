package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {super("Nem létezik ilyen felhasznalo :p");
    }
}
