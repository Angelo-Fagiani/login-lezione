package it.develhope.loginlezione.notification.services;

import it.develhope.loginlezione.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    JavaMailSender mailSender;

    public void sendActivationMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("angelo.develhope@gmail.com");
        message.setSubject("Ti sei iscritto alla piattaforma");
        message.setText("Il codice di attivazione è: " + user.getActivationCode());
        mailSender.send(message);
    }

    public void sendPasswordResetMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("angelo.develhope@gmail.com");
        message.setSubject("Reset Code");
        message.setText("Il codice di attivazione è: " + user.getPasswordResetCode());
        mailSender.send(message);
    }
}
