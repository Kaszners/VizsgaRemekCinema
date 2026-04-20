package hu.nyirszikszi.vizsgaremek.cinema.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class MediaStorageService {

    private final Path posterUploadDir = Path.of("uploads", "posters").toAbsolutePath().normalize();
    private final Path trailerUploadDir = Path.of("uploads", "trailers").toAbsolutePath().normalize();

    public MediaStorageService() {
        createDirectoryIfNeeded(posterUploadDir);
        createDirectoryIfNeeded(trailerUploadDir);
    }

    public String storePoster(MultipartFile file, Long movieId) {
        return store(file, movieId, posterUploadDir);
    }

    public String storeTrailer(MultipartFile file, Long movieId) {
        return store(file, movieId, trailerUploadDir);
    }

    public Resource loadPoster(String fileName) {
        return load(fileName, posterUploadDir);
    }

    public Resource loadTrailer(String fileName) {
        return load(fileName, trailerUploadDir);
    }

    public String probeContentType(Resource resource) {
        try {
            String type = Files.probeContentType(resource.getFile().toPath());
            return type != null ? type : "application/octet-stream";
        } catch (IOException exception) {
            return "application/octet-stream";
        }
    }

    private String store(MultipartFile file, Long movieId, Path targetDirectory) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A feltöltött fájl üres");
        }

        String cleanName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        String extension = "";
        int lastDot = cleanName.lastIndexOf('.');
        if (lastDot >= 0) {
            extension = cleanName.substring(lastDot);
        }

        String fileName = "movie-" + movieId + "-" + UUID.randomUUID() + extension;
        Path targetLocation = targetDirectory.resolve(fileName).normalize();

        if (!targetLocation.startsWith(targetDirectory)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Érvénytelen fájlnév");
        }

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException exception) {
            throw new UncheckedIOException("Nem sikerült menteni a feltöltött fájlt", exception);
        }
    }

    private Resource load(String fileName, Path targetDirectory) {
        try {
            Path file = targetDirectory.resolve(fileName).normalize();
            if (!file.startsWith(targetDirectory)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Érvénytelen fájlnév");
            }
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A kért fájl nem található");
            }
            return resource;
        } catch (IOException exception) {
            throw new UncheckedIOException("Nem sikerült betölteni a fájlt", exception);
        }
    }

    private void createDirectoryIfNeeded(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException exception) {
            throw new UncheckedIOException("Nem sikerült létrehozni a média könyvtárakat", exception);
        }
    }
}
