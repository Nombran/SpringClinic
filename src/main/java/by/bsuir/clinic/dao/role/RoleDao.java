package by.bsuir.clinic.dao.role;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role> {
    public Optional<Role> findByName(String name);
}
