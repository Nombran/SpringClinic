package by.bsuir.clinic.service.appointment;

import by.bsuir.clinic.dao.appointment.AppointmentDao;
import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.dto.TicketsCreationDto;
import by.bsuir.clinic.mapper.AppointmentMapper;
import by.bsuir.clinic.model.Appointment;
import by.bsuir.clinic.model.Doctor;
import by.bsuir.clinic.service.doctor.DoctorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao dao;
    private final AppointmentMapper mapper;
    private final DoctorDao doctorDao;


    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao,
                                  AppointmentMapper mapper,
                                  DoctorDao doctorDao) {
        this.dao = appointmentDao;
        this.mapper = mapper;
        this.doctorDao = doctorDao;
    }

    @Override
    public Optional<AppointmentDto> findById(Long id) {
        return dao.find(id).map(mapper::toDto);
    }

    @Override
    public void save(AppointmentDto appointmentDto) {
        dao.save(mapper.toEntity(appointmentDto));
    }

    @Override
    public List<AppointmentDto> findAll() {
        return dao.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppointmentDto> update(AppointmentDto appointmentDto) {
        Appointment updated = dao.update(mapper.toEntity(appointmentDto));
        return Optional.ofNullable(mapper.toDto(updated));
    }

    @Override
    public void delete(Long id) {
        Appointment appointment = dao.find(id).orElseThrow(
                ()-> new IllegalArgumentException("There is no appointment with id " + id)
        );
        dao.delete(appointment);
    }

    @Override
    public boolean save(TicketsCreationDto ticketsCreationDto) {
        LocalDateTime startTime = ticketsCreationDto.getStartTime();
        short gap = ticketsCreationDto.getGap();
        short ticketsNumber = ticketsCreationDto.getTicketsNumber();
        long doctorId = ticketsCreationDto.getDoctorId();
        Doctor doctor = doctorDao.find(doctorId).orElseThrow(
                ()-> new DoctorNotFoundException("Doctor with id + " + doctorId + "not exists")
        );
        LocalDateTime endTime = startTime.plusMinutes(ticketsNumber * gap);
        List<Appointment> appointmentsBetweenTime = dao.findAppointmentsByDoctorIdBetweenTime(doctorId,startTime, endTime);
        if(appointmentsBetweenTime.size() == 0) {
            LocalDateTime ticketTime = startTime;
            for(int i=0; i<ticketsNumber; i++) {
                Appointment appointment = new Appointment();
                appointment.setDateTime(ticketTime);
                appointment.setDoctor(doctor);
                dao.save(appointment);
                ticketTime = ticketTime.plusMinutes(gap);
            }
            return true;
        } else {
            return false;
        }
    }


}
