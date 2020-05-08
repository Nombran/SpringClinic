package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.dto.DoctorDto;
import by.bsuir.clinic.dto.TicketForCustomerDto;
import by.bsuir.clinic.model.Appointment;
import com.amazonaws.services.opsworks.model.App;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicketForCustomerMapper extends AbstractMapper<Appointment, TicketForCustomerDto> {

    private final ModelMapper mapper;

    @Autowired
    public TicketForCustomerMapper(ModelMapper mapper) {
        super(Appointment.class, TicketForCustomerDto.class);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Appointment.class, TicketForCustomerDto.class)
                .addMappings(m -> m.skip(TicketForCustomerDto::setCustomerDto)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TicketForCustomerDto::setDoctorDto)).setPostConverter(toDtoConverter());
    }

    @Override
    public void mapSpecificFields(Appointment source, TicketForCustomerDto destination) {
        CustomerDto customerDto = mapper.map(source.getCustomer(), CustomerDto.class);
        customerDto.setMedicalCard(null);
        customerDto.setAppointments(null);
        destination.setCustomerDto(customerDto);
        DoctorDto doctorDto = mapper.map(source.getDoctor(), DoctorDto.class);
        destination.setDoctorDto(doctorDto);
    }
}
