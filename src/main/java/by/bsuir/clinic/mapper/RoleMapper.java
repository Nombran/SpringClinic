package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.RoleDto;
import by.bsuir.clinic.model.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleMapper extends AbstractMapper<Role, RoleDto> {

    @Autowired
    public RoleMapper() {
        super(Role.class, RoleDto.class);
    }
}
