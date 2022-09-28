package it.develhope.loginlezione.auth.services;

import it.develhope.loginlezione.auth.entities.SignupActivationDTO;
import it.develhope.loginlezione.auth.entities.SignupDTO;
import it.develhope.loginlezione.notification.services.MailNotificationService;
import it.develhope.loginlezione.user.entities.Role;
import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.repositories.RoleRepository;
import it.develhope.loginlezione.user.repositories.UserRepository;
import it.develhope.loginlezione.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignupServices {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailNotificationService mailNotificationService;

    public User signup(SignupDTO signupDTO) throws Exception{
        return this.signup(signupDTO,Roles.REGISTERED);
    }
    public User signup(SignupDTO signupDTO,String role) throws Exception {
        User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        if (userInDB != null) throw new Exception("User already exist");

        User user = new User();
        user.setName(signupDTO.getName());
        user.setSurname(signupDTO.getSurname());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setEmail(signupDTO.getEmail());
        /** Genera un codice univoco a 36 caratteri per l'attivazione della email*/
        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(role.toUpperCase());
        if (!userRole.isPresent())throw new Exception("Role not found");
        roles.add(userRole.get());
        user.setRoles(roles);

        mailNotificationService.sendActivationMail(user);
        return userRepository.save(user);
    }

    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {
        User user = userRepository.findByActivationCode(signupActivationDTO.getActivationCode());
        if (user == null)throw new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
