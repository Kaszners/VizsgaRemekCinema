package hu.nyirszikszi.vizsgaremek.cinema.dto;

public record ChangeUsernameRequest(
        String currentPassword,
        String newUsername
) {
}
