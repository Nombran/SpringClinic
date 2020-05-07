package by.bsuir.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto extends AbstractDto{

    private String name;

    private String surname;

    private String lastName;

    private String phone;

    private MedicalCardDto medicalCardDto;

    private Long userId;

    private List<AppointmentDto> appointments;
}
