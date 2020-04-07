package by.bsuir.clinic.dto;

import by.bsuir.clinic.model.MedicalCard;
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
public class CustomerDto extends AbstractDto{

    private String name;

    private String surname;

    private String lastName;

    private String phone;

    private MedicalCardDto medicalCard;

    private UserDto user;

}
