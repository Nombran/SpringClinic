package by.bsuir.clinic.dao.role;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class RoleDaoImpl extends AbstractCrudDao<Role> implements RoleDao {

    public RoleDaoImpl() {
        setClazz(Role.class);
    }

    @Override
    public Optional<Role> findByName(String name) {
        Query query = entityManager.createQuery("From " + Role.class.getSimpleName() + " R where R.name=:nameParam");
        query.setParameter("nameParam", name);
        return Optional.ofNullable(query.getResultList().size() == 0 ? null : (Role)query.getResultList().get(0));
    }
}
