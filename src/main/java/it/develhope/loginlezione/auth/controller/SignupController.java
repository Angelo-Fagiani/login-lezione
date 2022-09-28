package it.develhope.loginlezione.auth.controller;

import it.develhope.loginlezione.auth.entities.SignupActivationDTO;
import it.develhope.loginlezione.auth.entities.SignupDTO;
import it.develhope.loginlezione.auth.services.SignupServices;
import it.develhope.loginlezione.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    private SignupServices signupServices;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupDTO signupDTO) throws Exception {
        return signupServices.signup(signupDTO);
    }
    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignupDTO signupDTO, @PathVariable String role) throws Exception {
        return signupServices.signup(signupDTO,role);
    }



    @PostMapping("/signup/activation")
    public User signup(@RequestBody SignupActivationDTO signupActivationDTO) throws Exception {
        return signupServices.activate(signupActivationDTO);
    }

}
