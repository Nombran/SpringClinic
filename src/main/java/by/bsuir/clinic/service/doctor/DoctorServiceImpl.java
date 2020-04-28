package by.bsuir.clinic.service.doctor;

import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.mapper.DoctorMapper;
import by.bsuir.clinic.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorDao dao;
    private final DoctorMapper mapper;

    @Autowired
    public DoctorServiceImpl(DoctorDao dao,
                             DoctorMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
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
    public void save(DoctorDto doctor) {
        dao.save(mapper.toEntity(doctor));
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
        dao.delete(doctorForDelete);
    }
}
