package it.develhope.loginlezione.auth.controller;

import it.develhope.loginlezione.auth.entities.RequestPasswordDTO;
import it.develhope.loginlezione.auth.entities.RestorePasswordDTO;
import it.develhope.loginlezione.auth.services.PasswordService;
import it.develhope.loginlezione.notification.services.MailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {

    @Autowired
    private PasswordService passwordService;


    /** Quando l'utente fa una richiesta per una nuova password*/
    @PostMapping("/request")
    public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO)throws Exception{
        try {
            passwordService.request(requestPasswordDTO);
        }catch (Exception e){

        }


    }
    /** Quando la password viene restaurata*/
    @PostMapping("/restore")
    public void passwordRestore(@RequestBody RestorePasswordDTO restorePasswordDTO)throws Exception{
        passwordService.restore(restorePasswordDTO);
    }

}
