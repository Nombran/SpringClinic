package by.bsuir.clinic.service;

import by.bsuir.clinic.dao.role.RoleDao;
import by.bsuir.clinic.dao.user.UserDaoImpl;
import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.mapper.UserMapper;
import by.bsuir.clinic.model.Role;
import by.bsuir.clinic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private UserDaoImpl dao;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    private RoleDao roleDao;

    @Autowired
    public UserService(UserDaoImpl dao,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       RoleDao roleDao) {

        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleDao = roleDao;
    }

    public Optional<UserDto> findByUsername(String login) {
            return dao.findByUsername(login)
                    .map(user -> userMapper.toDto(user));
    }

    public void save(UserDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = userMapper.toEntity(dto);
        dao.save(user);
    }

    public Optional<UserDto> findById(long id) {
        return dao.find(id).map(user -> userMapper.toDto(user));
    }

    public List<UserDto> findAll() {
        return dao.findAll().stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    public UserDto update(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        User updated = dao.update(entity);
        return userMapper.toDto(updated);
    }
}
