package by.bsuir.clinic.dao.appointment;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Appointment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
public class AppointmentDaoImpl extends AbstractCrudDao<Appointment> implements AppointmentDao {

    public AppointmentDaoImpl() {
        setClazz(Appointment.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAppointmentByCustomerId(long customerId) {
        Query query = entityManager.createQuery("From "
                + Appointment.class.getSimpleName() + " A where A.customer.id=:customerId");
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
