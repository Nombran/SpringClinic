package by.bsuir.clinic.dao.doctor;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Doctor;

import java.util.List;

public interface DoctorDao extends CrudDao<Doctor> {
    List<Doctor> findDoctorsByDepartmentId(Long id);
}
