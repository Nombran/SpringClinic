package by.bsuir.clinic.dao.user;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;
import java.util.Optional;

@Transactional
@Repository
public class UserDaoImpl extends AbstractCrudDao<User> implements UserDao{

    public UserDaoImpl() {
        setClazz(User.class);
    }

    public Optional<User> findByUsername(String login) {
        Query query = entityManager.createQuery("From " + User.class.getSimpleName() + " U where U.username=:usernameParam");
        query.setParameter("usernameParam", login);
        return Optional.ofNullable(query.getResultList().size() == 0 ? null : (User)query.getResultList().get(0));
    }
}
