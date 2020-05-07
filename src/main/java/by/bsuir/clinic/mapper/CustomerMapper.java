package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class CustomerMapper extends AbstractMapper<Customer, CustomerDto> {

    @Autowired
    public CustomerMapper() {
        super(Customer.class, CustomerDto.class);
    }

    @Override
    void mapSpecificFields(Customer source, CustomerDto destination) {
        long userId = source.getUser().getId();
        destination.setUserId(userId);
        destination.setAppointments(null);
        destination.setMedicalCardDto(null);
    }
}
