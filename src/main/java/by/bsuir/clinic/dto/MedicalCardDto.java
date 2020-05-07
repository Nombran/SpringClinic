package by.bsuir.clinic.dto;

import by.bsuir.clinic.model.Gender;
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
public class MedicalCardDto extends AbstractDto {

    private Long customerId;

    private String registration;

    private Short height;

    private Short weight;

    private String chronicDiseases;

    private Gender gender;

    private String allergies;

    private LocalDateTime birthday;
}
