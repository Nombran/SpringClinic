package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.AuthenticationRequestDto;
import by.bsuir.clinic.dto.RoleDto;
import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.security.jwt.JwtTokenProvider;
import by.bsuir.clinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDto user = userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username: "
                            + username + " not found"));

            String token = jwtTokenProvider.createToken(username, new ArrayList<>(user.getRoles()));

            Map<Object, Object> response = new HashMap<>();

            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);
        }
    }

    @PostMapping(value = "register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@RequestBody UserDto user) {
        user.setRoles(Collections.singletonList("ROLE_USER"));
        userService.save(user);
    }

    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Username already exists")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
    }
}
