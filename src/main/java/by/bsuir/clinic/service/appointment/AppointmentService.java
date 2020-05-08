package by.bsuir.clinic.service.appointment;

import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.dto.TicketsCreationDto;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Optional<AppointmentDto> findById(Long id);
    void save(AppointmentDto appointmentDto);
    List<AppointmentDto> findAll();
    Optional<AppointmentDto> update(AppointmentDto appointmentDto);
    void delete(Long id);
    boolean save(TicketsCreationDto ticketsCreationDto);
}
