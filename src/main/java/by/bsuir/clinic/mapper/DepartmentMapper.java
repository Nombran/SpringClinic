package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.model.Department;
import by.bsuir.clinic.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Component
public class DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {

    private final ModelMapper mapper;
    private final DoctorDao doctorDao;

    @Autowired
    public DepartmentMapper(ModelMapper mapper, DoctorDao doctorDao) {
        super(Department.class, DepartmentDto.class);
        this.doctorDao = doctorDao;
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Department.class, DepartmentDto.class)
                .addMappings(m -> m.skip(DepartmentDto::setDoctors)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DepartmentDto.class, Department.class)
                .addMappings(m -> m.skip(Department::setDoctors)).setPostConverter(toEntityConverter());
    }

    private Collection<Doctor> findDoctorsByDepartmentId(DepartmentDto source) {
        return doctorDao.findDoctorsByDepartmentId(source.getId());
    }

    @Override
    void mapSpecificFields(DepartmentDto source, Department destination) {
        Collection<Doctor> doctors = findDoctorsByDepartmentId(source);
        destination.setDoctors(doctors);
    }
}
