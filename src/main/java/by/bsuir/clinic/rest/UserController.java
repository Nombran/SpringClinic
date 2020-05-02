package by.bsuir.clinic.rest;

import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.security.jwt.JwtTokenProvider;
import by.bsuir.clinic.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private JwtTokenProvider tokenProvider;

    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service,
                          JwtTokenProvider tokenProvider) {
        this.service = service;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public List<UserDto> findAll() {
        return service.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDto user) {
        service.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal.id == #id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody UserDto userDto, @PathVariable Long id){
        userDto.setId(id);
        if(!service.update(userDto).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update this user");
        }
    }

    @GetMapping(value = "/{id}")
    public UserDto findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @GetMapping(value = "/me")
    public UserDto findByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = tokenProvider.getTokenFromHeader(authorizationHeader);
        System.out.println(token);
        String username = tokenProvider.getUsername(token);
        return service.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Username already exists")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST,
            reason="Such role doesnt exists")
    @ExceptionHandler(IllegalArgumentException.class)
    public void roleNotExists() {
    }
}
