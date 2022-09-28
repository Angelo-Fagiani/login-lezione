package it.develhope.loginlezione.user.controller;

import it.develhope.loginlezione.auth.entities.LoginRTO;
import it.develhope.loginlezione.auth.services.LoginService;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public LoginRTO getProfile(Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        LoginRTO loginRTO = new LoginRTO();
        loginRTO.setUser(user);
        loginRTO.setJWT(loginService.generateJWT(user));
        return loginRTO;
    }

}
