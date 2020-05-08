package by.bsuir.clinic.dao.appointment;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDao extends CrudDao<Appointment> {
    List<Appointment> findAppointmentByCustomerId(long customerId);
    List<Appointment> findAppointmentsBetweenTime(LocalDateTime startTime,
                                                  LocalDateTime endTime);
    List<Appointment> findAppointmentsByCustomerId(long customerId);
    List<Appointment> findAppointmentsByDoctorIdBetweenTime(long doctorId,
                                                            LocalDateTime startTime,
                                                            LocalDateTime endTime);
    List<Appointment> findAppointmentsByDoctorId(long doctorId);
}
