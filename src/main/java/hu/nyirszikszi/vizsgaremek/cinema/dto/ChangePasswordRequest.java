package hu.nyirszikszi.vizsgaremek.cinema.dto;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
