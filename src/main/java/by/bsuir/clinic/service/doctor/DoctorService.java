package by.bsuir.clinic.service.doctor;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.dto.TicketForDoctorDto;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<DoctorDto> findAll();
    Optional<DoctorDto> findById(Long id);
    long save(DoctorDto doctor);
    Optional<DoctorDto> update(DoctorDto doctor);
    void delete(Long id);
    Optional<DoctorDto> findDoctorByUserId(long userId);
    List<TicketForDoctorDto> findDoctorAppointmentsByDay(long doctorId, LocalDate day);
    List<TicketForDoctorDto> findDoctorAppointments(long doctorId);
}
