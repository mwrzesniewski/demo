package pl.wrzesniewski.demo.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wrzesniewski.demo.user.User;
import pl.wrzesniewski.demo.user.UserRepository;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_" + USER;
    private final UserRepository userRepository;

    // ...

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User client = userRepository.findOneByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        return new JwtUserDetails(client.getId(), username, client.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }

}
