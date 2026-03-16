package hu.nyirszikszi.vizsgaremek.cinema.entity;


import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cinema_booking",
       indexes = {
        @Index(
                name = "idx_booking_seat_showtime",
               columnList = "seat_id, showtime_id"
        ),
        @Index(
                name = "idx_booking_showtime",
               columnList = "showtime_id"
        ),
        @Index(
                name = "idx_booking_status_time",
                columnList = "booking_status, created"
        )
    }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(name = "created")
    private LocalDateTime timeOfCreation;

    @Column(name = "booking_confirmation_token")
    private String confirmationToken;



}
