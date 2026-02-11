package hu.nyirszikszi.vizsgaremek.cinema.entity;

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
@Table(name = "cinema_user_credentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false,unique = true)
    private User userId;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;




}
