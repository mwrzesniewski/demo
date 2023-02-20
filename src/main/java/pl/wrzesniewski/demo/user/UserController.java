package pl.wrzesniewski.demo.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wrzesniewski.demo.user.dto.UserDto;
import pl.wrzesniewski.demo.user.exception.UserExistsException;
import pl.wrzesniewski.demo.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changeUserPassword(@PathVariable Long id, String password) throws UserNotFoundException {
        userService.changeUserPassword(id, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Void> addUser(@RequestBody @Valid final UserDto userDto) throws UserExistsException {
        try {
            userService.addUser(userDto);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User exists", ex);
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/registration")
    public ResponseEntity<Void> registerNewUser(@RequestBody @Valid final UserRegistrationDto registrationDtoDto) {
        try {
            userService.register(registrationDtoDto);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User exists", ex);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);

            Page<User> records = userService.findByActive(true, paging);
            List<User> users = records.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("currentPage", records.getNumber());
            response.put("totalItems", records.getTotalElements());
            response.put("totalPages", records.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
