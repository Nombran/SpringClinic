package by.bsuir.clinic.dao.appointment;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Appointment;

import java.util.List;

public interface AppointmentDao extends CrudDao<Appointment> {
    List<Appointment> findAppointmentByCustomerId(long customerId);
}
