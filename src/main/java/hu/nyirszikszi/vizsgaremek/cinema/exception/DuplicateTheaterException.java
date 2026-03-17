package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class DuplicateTheaterException extends RuntimeException {
    public DuplicateTheaterException() {
        super("mar van ilyen elnevezesu terem");
    }
}
