package hu.nyirszikszi.vizsgaremek.cinema.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    public void sendBookingConfirmation(String email, String token){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("sebestyenkaszner@gmail.com");
        message.setTo(email);
        message.setSubject("Confirm your cinema Booking");

        message.setText(
                "please confirm your booking:\n\n+" +
                "http://localhost:8080/cinema/booking/confirm/"+token
        );
        mailSender.send(message);
    }

}
