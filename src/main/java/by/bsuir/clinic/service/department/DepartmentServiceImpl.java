package by.bsuir.clinic.service.department;

import by.bsuir.clinic.dao.department.DepartmentDao;
import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.mapper.DepartmentMapper;
import by.bsuir.clinic.mapper.DepartmentWithDoctorsMapper;
import by.bsuir.clinic.mapper.DoctorMapper;
import by.bsuir.clinic.model.Department;
import by.bsuir.clinic.model.Doctor;
import by.bsuir.clinic.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentDao dao;
    private final DepartmentMapper departmentMapper;
    private final DoctorMapper doctorMapper;
    private final FileManager fileManager;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao dao,
                                 FileManager fileManager,
                                 DepartmentMapper departmentMapper,
                                 DoctorMapper doctorMapper) {
        this.dao = dao;
        this.fileManager = fileManager;
        this.departmentMapper = departmentMapper;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public Optional<DepartmentDto> findDepartmentById(Long id) {
        Optional<Department> departmentOptional = dao.findDepartmentById(id);
        if(departmentOptional.isPresent()) {
           Department department = departmentOptional.get();
           Collection<DoctorDto> doctors = department.getDoctors()
                   .stream()
                   .map(doctorMapper::toDto)
                   .collect(Collectors.toList());
           DepartmentDto departmentDto = departmentMapper.toDto(department);
           departmentDto.setDoctors(doctors);
           return Optional.of(departmentDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public long save(DepartmentDto dto) {
        Department entity = departmentMapper.toEntity(dto);
        dao.save(entity);
        return entity.getId();
    }

    @Override
    public List<DepartmentDto> findAll() {
        return dao.findAll()
                .stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDto> update(DepartmentDto departmentDto) {
        Optional<Department> optionalDepartment = dao.findDepartmentById(departmentDto.getId());
        if(!optionalDepartment.isPresent()) {
            throw new IllegalArgumentException("Department with id "
                    + departmentDto.getId()
                    + "not found");
        }
        Department departmentBeforeUpdate = optionalDepartment.get();
        if(departmentBeforeUpdate.getImageUrl() != null) {
            if (!departmentBeforeUpdate.getImageUrl().equals(departmentDto.getImageUrl())) {
                String imageUrlBefore = departmentBeforeUpdate.getImageUrl();
                fileManager.deleteFileFromS3Bucket(imageUrlBefore);
            }
        }
        Department withUpdates = departmentMapper.toEntity(departmentDto);
        Department updated = dao.update(withUpdates);
        return Optional.ofNullable(departmentMapper.toDto(updated));
    }

    public void delete(Long id) {
        Optional<Department> optionalDepartment = dao.findDepartmentById(id);
        Department forDelete = optionalDepartment.orElseThrow(IllegalArgumentException::new);
        String imageUrl = forDelete.getImageUrl();
        if(imageUrl != null) {
            fileManager.deleteFileFromS3Bucket(imageUrl);
        }
        dao.delete(forDelete);
    }
}

