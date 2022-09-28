package it.develhope.loginlezione.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    /** This is the user email*/
    private String email;
    /** This is the user password clear*/
    private String password;


}
