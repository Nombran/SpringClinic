package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.DepartmentDto;
import by.bsuir.clinic.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentWithDoctorsMapper extends AbstractMapper<Department, DepartmentDto> {

    @Autowired
    public DepartmentWithDoctorsMapper() {
        super(Department.class, DepartmentDto.class);
    }
}
