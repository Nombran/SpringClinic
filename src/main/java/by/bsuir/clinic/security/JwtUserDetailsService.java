package by.bsuir.clinic.security;

import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.model.User;
import by.bsuir.clinic.security.jwt.JwtUser;
import by.bsuir.clinic.security.jwt.JwtUserFactory;
import by.bsuir.clinic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> optionalUser = userService.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        UserDto user = optionalUser.get();
        return JwtUserFactory.create(user);
    }
}
