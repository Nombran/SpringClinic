package by.bsuir.clinic.dto;

import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDto extends AbstractDto {

    private CustomerDto customer;

    private DoctorDto doctor;

    private LocalDateTime dateTime;

    private String diagnosis;

    private String reason;

    private MedicalCardDto medicalCard;
}
