package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    public void sendBookingConfirmation(String email, String token, List<Booking> bookings) {
        Context context = new Context();

        context.setVariable("username", bookings.get(0).getUser().getFullName());
        context.setVariable("showtime", bookings.get(0).getShowtime().getShowStartTime());

        context.setVariable("seats", bookings.stream()
                .map(Booking::getSeat)
                .toList()
        );

        context.setVariable("confirmUrl",
                "http://localhost:8080/cinema/booking/confirm?token=" + token
        );

        String html = templateEngine.process("booking-confirmation", context);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Confirm your booking");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
