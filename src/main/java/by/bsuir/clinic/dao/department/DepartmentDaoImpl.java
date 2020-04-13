package by.bsuir.clinic.dao.department;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Department;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import java.util.Optional;

@Repository
@Transactional
public class DepartmentDaoImpl extends AbstractCrudDao<Department> implements DepartmentDao {

    public DepartmentDaoImpl() {
        setClazz(Department.class);
    }

    @Override
    public Optional<Department> findDepartmentById(Long id) {
        Query query = entityManager.createQuery("From "
                + Department.class.getSimpleName()
                + " D where D.id=:id");
        query.setParameter("id", id);
        return Optional.ofNullable(query.getResultList().size() == 0 ?
                null
                : (Department)query.getResultList().get(0));
    }
}
