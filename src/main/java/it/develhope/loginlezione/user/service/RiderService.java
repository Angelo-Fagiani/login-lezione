package it.develhope.loginlezione.user.service;

import it.develhope.loginlezione.user.entities.User;
import it.develhope.loginlezione.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;


    public User pickRider() {
        Optional<User> rider = userRepository.pickRider();
        if (rider.isPresent()){
            //THERE IS A FREE RIDER
            return rider.get();
        }else {
            //ALL RIDERS ARE BUSY -> TAKE THE FIRST RIDER AVAIABLE
            return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
        }

    }
}
