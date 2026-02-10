package hu.nyirszikszi.vizsgaremek.cinema.entity;


import hu.nyirszikszi.vizsgaremek.cinema.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cinema_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;



}
