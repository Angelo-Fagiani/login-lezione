package it.develhope.loginlezione.auth.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    /** User name */
    private String name;
    /** User surname */
    private String surname;
    /** User email clear */
    private String email;
    /** User password clear */
    private String password;


}
