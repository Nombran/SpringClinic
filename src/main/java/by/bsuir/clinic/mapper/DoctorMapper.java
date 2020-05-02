package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.department.DepartmentDao;
import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dao.user.UserDao;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.model.Department;
import by.bsuir.clinic.model.Doctor;
import by.bsuir.clinic.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DoctorMapper extends AbstractMapper<Doctor, DoctorDto> {
    private final ModelMapper mapper;
    private final DepartmentDao departmentDao;
    private final UserDao userDao;


    @Autowired
    public DoctorMapper(ModelMapper mapper,
                            DepartmentDao departmentDao,
                            UserDao userDao) {
        super(Doctor.class, DoctorDto.class);
        this.mapper = mapper;
        this.departmentDao = departmentDao;
        this.userDao = userDao;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Doctor.class, DoctorDto.class)
                .addMappings(m -> m.skip(DoctorDto::setUserId))
                .addMappings(m -> m.skip(DoctorDto::setDepartmentId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(DoctorDto.class, Doctor.class)
                .addMappings(m -> m.skip(Doctor::setUser))
                .addMappings(m -> m.skip(Doctor::setDepartment))
                .setPostConverter(toEntityConverter());
    }


    @Override
    void mapSpecificFields(DoctorDto source, Doctor destination) {
        Long departmentId = source.getDepartmentId();
        Department department = departmentDao.findDepartmentById(departmentId)
                .orElseThrow(
                        ()-> new MapperException("Error while mapping:" +
                                " department with id = " + departmentId + " not found")
                );
        Long userId = source.getUserId();
        User user = userDao.find(userId).orElseThrow(
                ()-> new MapperException("Error while mapping:" +
                        " user with id = " + userId + " not found")
        );
        destination.setDepartment(department);
        destination.setUser(user);
    }

    @Override
    void mapSpecificFields(Doctor source, DoctorDto destination) {
        Long departmentId = source.getDepartment().getId();
        destination.setDepartmentId(departmentId);
        Long userId = source.getUser().getId();
        destination.setUserId(userId);
    }

}
