package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.model.Department;
import by.bsuir.clinic.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.stream.Collectors;


public class DepartmentWithDoctorsMapper extends DepartmentMapper{

    private final DoctorMapper doctorMapper;


    public DepartmentWithDoctorsMapper(ModelMapper mapper,
                                       DoctorDao doctorDao,
                                       DoctorMapper doctorMapper) {
        super(mapper, doctorDao);
        this.doctorMapper = doctorMapper;
    }

    @Override
    void mapSpecificFields(Department source, DepartmentDto destination) {
        Collection<Doctor> doctorList = source.getDoctors();
        if(doctorList != null) {
            Collection<DoctorDto> doctorDtos = doctorList.stream()
                    .map(doctorMapper::toDto)
                    .collect(Collectors.toList());
            destination.setDoctors(doctorDtos);
        }

    }
}
