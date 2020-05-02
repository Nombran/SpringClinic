package by.bsuir.clinic.service.user;

import by.bsuir.clinic.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> findByUsername(String login);
    void save(UserDto dto);
    Optional<UserDto> findById(long id);
    List<UserDto> findAll();
    Optional<UserDto> update(UserDto userDto);
}
