package pl.wrzesniewski.demo.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.wrzesniewski.demo.user.dto.UserDto;
import pl.wrzesniewski.demo.user.exception.UserExistsException;
import pl.wrzesniewski.demo.user.exception.UserNotFoundException;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return this.userRepository.findOneByEmail(email).orElseThrow(()->new UserNotFoundException("User with email:"+ email +" not found"));
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return this.userRepository.findOneById(id).orElseThrow(()->new UserNotFoundException("User with id:"+ id +" not found"));
    }

    public void removeUser(Long id) {
        this.userRepository.deleteUserById(id);
    }

    @Override
    public void changeUserPassword(Long id, String password) throws UserNotFoundException {
        this.getUserById(id).setPassword(password);
    }

    @Override
    public Page<User> findByActive(boolean active, Pageable paging) {
        return this.userRepository.findByActive(true, paging);
    }

    @Override
    public User addUser(UserDto dto) throws UserExistsException {

        if(this.userRepository.findOneByEmail(dto.getEmail()).isPresent()){
            throw new UserExistsException("User with email "+ dto.getEmail() + " already exists");
        }
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);
        return user;
    }

}
