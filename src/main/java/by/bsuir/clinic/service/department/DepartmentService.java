package by.bsuir.clinic.service.department;

import by.bsuir.clinic.dto.DepartmentDto;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<DepartmentDto> findDepartmentById(Long id);
    long save(DepartmentDto departmentDto);
    List<DepartmentDto> findAll();
    Optional<DepartmentDto> update(DepartmentDto departmentDto);
    void delete(Long id);
}
