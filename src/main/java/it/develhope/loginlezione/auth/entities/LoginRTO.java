package it.develhope.loginlezione.auth.entities;

import it.develhope.loginlezione.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRTO {

    private User user;
    private String JWT;

}
