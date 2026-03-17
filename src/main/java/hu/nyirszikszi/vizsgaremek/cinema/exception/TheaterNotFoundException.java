package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class TheaterNotFoundException extends RuntimeException {
    public TheaterNotFoundException() {
        super("Nincs vetitio ilyen id-val");
    }
}
