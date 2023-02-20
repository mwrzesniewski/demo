package pl.wrzesniewski.demo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.wrzesniewski.demo.user.dto.UserDto;
import pl.wrzesniewski.demo.user.exception.UserExistsException;
import pl.wrzesniewski.demo.user.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserByEmail(String email) throws UserNotFoundException;
    User getUserById(Long id) throws UserNotFoundException;
    User addUser(UserDto dto) throws UserExistsException;
    void removeUser(Long id);
    void changeUserPassword(Long id, String password) throws UserNotFoundException;

    Page<User> findByActive(boolean active, Pageable paging);

    User register(UserRegistrationDto registrationDtoDto) throws UserExistsException;
}
