package hu.nyirszikszi.vizsgaremek.cinema.entity;


import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cinema_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;

    private String fullName;



}
