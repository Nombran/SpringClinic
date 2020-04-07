package by.bsuir.clinic.dto;

import by.bsuir.clinic.model.Department;
import by.bsuir.clinic.model.MedicalCategory;
import by.bsuir.clinic.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDto extends AbstractDto {

    private String name;

    private String surname;

    private String lastName;

    private String specialization;

    private MedicalCategory medicalCategory;

    private UserDto user;

    private DepartmentDto department;
}
