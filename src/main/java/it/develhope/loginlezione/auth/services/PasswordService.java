package it.develhope.loginlezione.auth.services;

import it.develhope.loginlezione.auth.entities.RequestPasswordDTO;
import it.develhope.loginlezione.auth.entities.RestorePasswordDTO;
import it.develhope.loginlezione.notification.services.MailNotificationService;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailNotificationService mailNotificationService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User request(RequestPasswordDTO requestPasswordDTO) throws Exception {
    User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
    if (userFromDB == null) throw new Exception("User not found");
    userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
    mailNotificationService.sendPasswordResetMail(userFromDB);
    return userRepository.save(userFromDB);


    }
    public User restore(RestorePasswordDTO restorePasswordDTO)throws Exception{
       User userFomDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        if (userFomDB == null) throw new Exception("User not found");
        userFomDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFomDB.setPasswordResetCode(null);

        //ho attivato l'utente
        userFomDB.setActive(true);
        userFomDB.setActivationCode(null);

        return userRepository.save(userFomDB);
    }
}
