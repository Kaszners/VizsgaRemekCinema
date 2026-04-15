package hu.nyirszikszi.vizsgaremek.cinema.service;

import hu.nyirszikszi.vizsgaremek.cinema.entity.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendBookingConfirmation(String email, String token, List<Booking> bookings) {
        Context context = new Context();

        context.setVariable("username", bookings.get(0).getUser().getFullName());
        context.setVariable("showtime", bookings.get(0).getShowtime().getShowStartTime());
        context.setVariable("seats", bookings.stream()
                .map(Booking::getSeat)
                .toList());
        context.setVariable("confirmUrl",
                "http://localhost:8080/cinema/booking/confirm?token=" + token);

        String html = templateEngine.process("booking-confirmation", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("Confirm your booking");
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Booking confirmation email sent to {}", email);

        } catch (MailException | MessagingException e) {
            log.error("Failed to send booking confirmation email to {}", email, e);
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }
}