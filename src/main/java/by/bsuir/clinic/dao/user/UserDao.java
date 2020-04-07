package by.bsuir.clinic.dao.user;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User> {
    public Optional<User> findByUsername(String login);
}
