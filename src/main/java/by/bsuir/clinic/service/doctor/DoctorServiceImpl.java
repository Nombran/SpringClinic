package by.bsuir.clinic.service.doctor;

import by.bsuir.clinic.dao.appointment.AppointmentDao;
import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.dto.TicketForDoctorDto;
import by.bsuir.clinic.mapper.DoctorMapper;
import by.bsuir.clinic.mapper.TicketForDoctorMapper;
import by.bsuir.clinic.model.Doctor;
import by.bsuir.clinic.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorDao dao;
    private final DoctorMapper mapper;
    private final FileManager fileManager;
    private final AppointmentDao appointmentDao;
    private final TicketForDoctorMapper ticketForDoctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorDao dao,
                             DoctorMapper mapper,
                             FileManager fileManager,
                             AppointmentDao appointmentDao,
                             TicketForDoctorMapper ticketForDoctorMapper) {
        this.dao = dao;
        this.mapper = mapper;
        this.fileManager = fileManager;
        this.appointmentDao = appointmentDao;
        this.ticketForDoctorMapper = ticketForDoctorMapper;
    }


    @Override
    public List<DoctorDto> findAll() {
        return dao.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorDto> findById(Long id) {
        return dao.find(id).map(mapper::toDto);
    }

    @Override
    public long save(DoctorDto doctor) {
        Doctor entity = mapper.toEntity(doctor);
        dao.save(entity);
        return entity.getId();
    }

    @Override
    public Optional<DoctorDto> update(DoctorDto doctor) {
        Doctor entity = mapper.toEntity(doctor);
        Doctor updated = dao.update(entity);
        return Optional.ofNullable(mapper.toDto(updated));
    }

    @Override
    public void delete(Long id) {
        Optional<Doctor> doctor = dao.find(id);
        Doctor doctorForDelete = doctor.orElseThrow(IllegalArgumentException::new);
        String imageUrl = doctorForDelete.getImageUrl();
        if(imageUrl != null) {
            fileManager.deleteFileFromS3Bucket(imageUrl);
        }
        dao.delete(doctorForDelete);
    }

    @Override
    public Optional<DoctorDto> findDoctorByUserId(long userId) {
        return dao.findDoctorByUserId(userId).map(mapper::toDto);
    }

    @Override
    public List<TicketForDoctorDto> findDoctorAppointmentsByDay(long doctorId, LocalDate day) {
        LocalDateTime startTime = LocalDateTime.of(day, LocalTime.of(8,0));
        LocalDateTime endTime = LocalDateTime.of(day, LocalTime.of(23, 59));
        return appointmentDao.findAppointmentsByDoctorIdBetweenTime(doctorId,startTime,endTime)
                .stream()
                .map(ticketForDoctorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketForDoctorDto> findDoctorAppointments(long doctorId) {
        return appointmentDao.findAppointmentsByDoctorId(doctorId)
                .stream()
                .map(ticketForDoctorMapper::toDto)
                .collect(Collectors.toList());
    }
}
