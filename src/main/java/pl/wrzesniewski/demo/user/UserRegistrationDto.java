package pl.wrzesniewski.demo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserRegistrationDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
