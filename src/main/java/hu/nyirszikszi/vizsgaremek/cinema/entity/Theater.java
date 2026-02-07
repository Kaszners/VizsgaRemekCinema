package hu.nyirszikszi.vizsgaremek.cinema.entity;


import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;
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
@Table(name = "cinema_theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "theater_size", nullable = false)
    private TheaterSize size;

}
