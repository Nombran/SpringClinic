package by.bsuir.clinic.dao.appointment;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Appointment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AppointmentDaoImpl extends AbstractCrudDao<Appointment> implements AppointmentDao {

    public AppointmentDaoImpl() {
        setClazz(Appointment.class);
    }
}
