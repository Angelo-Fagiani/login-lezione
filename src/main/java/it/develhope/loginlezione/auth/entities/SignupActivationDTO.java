package it.develhope.loginlezione.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupActivationDTO {

    private String activationCode;


}
