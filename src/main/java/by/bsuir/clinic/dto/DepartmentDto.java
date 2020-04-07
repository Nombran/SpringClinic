package by.bsuir.clinic.dto;

import by.bsuir.clinic.model.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto extends AbstractDto {

    private String name;

    private String description;

    private String phone;

    private Set<DoctorDto> doctors;
}
