package by.bsuir.clinic.dao.doctor;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Doctor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class DoctorDaoImpl extends AbstractCrudDao<Doctor> implements DoctorDao {

    public DoctorDaoImpl() {
        setClazz(Doctor.class);
    }

    @SuppressWarnings("unchecked")
    public List<Doctor> findDoctorsByDepartmentId(Long id) {
        Query query = entityManager.createQuery("From "
                + Doctor.class.getSimpleName()
                + " D where D.department.id=:id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
