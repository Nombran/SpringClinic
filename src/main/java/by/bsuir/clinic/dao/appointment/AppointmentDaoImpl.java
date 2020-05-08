package by.bsuir.clinic.dao.appointment;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Appointment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.time.LocalDateTime;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAppointmentsBetweenTime(LocalDateTime startTime, LocalDateTime endTime) {
        Query query = entityManager.createQuery("From "
                + Appointment.class.getSimpleName()
                + " A where A.dateTime BETWEEN :startTime AND :endTime");
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAppointmentsByCustomerId(long customerId) {
        Query query = entityManager.createQuery("From "
                + Appointment.class.getSimpleName()
                + " A where A.customer.id=:id");
        query.setParameter("id", customerId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAppointmentsByDoctorIdBetweenTime(long doctorId,
                                                                   LocalDateTime startTime,
                                                                   LocalDateTime endTime) {
        Query query = entityManager.createQuery("From "
                + Appointment.class.getSimpleName()
                + " A where A.doctor.id=:id AND" +
                " A.dateTime BETWEEN :startTime AND :endTime");
        query.setParameter("id", doctorId);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Appointment> findAppointmentsByDoctorId(long doctorId) {
        Query query = entityManager.createQuery("From "
                + Appointment.class.getSimpleName()
                + " A where A.doctor.id=:doctorId");
        query.setParameter("doctorId",doctorId);
        return query.getResultList();
    }

}
