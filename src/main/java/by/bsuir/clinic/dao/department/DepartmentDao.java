package by.bsuir.clinic.dao.department;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Department;

import java.util.Optional;

public interface DepartmentDao extends CrudDao<Department> {
    public Optional<Department> findDepartmentById(Long id);
 }
