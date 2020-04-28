package by.bsuir.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto extends AbstractDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String phone;

    private String imageUrl;

    private Collection<DoctorDto> doctors;
}
