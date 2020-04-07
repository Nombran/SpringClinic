package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.role.RoleDao;
import by.bsuir.clinic.dto.UserDto;
import by.bsuir.clinic.model.Role;
import by.bsuir.clinic.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    private final ModelMapper mapper;
    private final RoleDao roleDao;

    @Autowired
    public UserMapper(ModelMapper mapper, RoleDao roleDao) {
        super(User.class, UserDto.class);
        this.mapper = mapper;
        this.roleDao = roleDao;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setRoles)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setRoles)).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(User source, UserDto destination) {
        destination.setRoles(getRoleNames(source));
    }

    private Collection<String> getRoleNames(User source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @Override
    void mapSpecificFields(UserDto source,User destination) {
        destination.setRoles(source.getRoles()
                .stream()
                .map(role -> roleDao.findByName(role).orElseThrow(IllegalArgumentException::new))
                .collect(Collectors.toList()));
    }
}
