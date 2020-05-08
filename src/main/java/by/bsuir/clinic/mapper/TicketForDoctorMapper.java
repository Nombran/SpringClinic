package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.dto.TicketForDoctorDto;
import by.bsuir.clinic.model.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicketForDoctorMapper extends AbstractMapper<Appointment, TicketForDoctorDto> {
    private final ModelMapper mapper;

    @Autowired
    public TicketForDoctorMapper(ModelMapper mapper) {
        super(Appointment.class, TicketForDoctorDto.class);
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Appointment.class, TicketForDoctorDto.class)
                .addMappings(m -> m.skip(TicketForDoctorDto::setCustomerDto)).setPostConverter(toDtoConverter());
    }

    @Override
    public void mapSpecificFields(Appointment source, TicketForDoctorDto destination) {
        if(source.getCustomer()!=null) {
            CustomerDto customerDto = mapper.map(source.getCustomer(), CustomerDto.class);
            customerDto.setMedicalCard(null);
            customerDto.setAppointments(null);
            destination.setCustomerDto(customerDto);
        }
    }
}
