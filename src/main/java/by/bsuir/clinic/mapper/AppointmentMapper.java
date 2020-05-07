package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.customer.CustomerDao;
import by.bsuir.clinic.dao.doctor.DoctorDao;
import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.model.Appointment;
import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppointmentMapper extends AbstractMapper<Appointment, AppointmentDto> {

    private final DoctorDao doctorDao;
    private final CustomerDao customerDao;
    private final ModelMapper mapper;

    @Autowired
    public AppointmentMapper(DoctorDao doctorDao,
                             CustomerDao customerDao,
                             ModelMapper mapper) {
        super(Appointment.class,AppointmentDto.class);
        this.doctorDao = doctorDao;
        this.customerDao = customerDao;
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Appointment.class, AppointmentDto.class)
                .addMappings(m -> m.skip(AppointmentDto::setCustomerId)).setPostConverter(toDtoConverter())
                .addMappings(m-> m.skip(AppointmentDto::setDoctorId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(AppointmentDto.class, Appointment.class)
                .addMappings(m -> m.skip(Appointment::setCustomer)).setPostConverter(toEntityConverter())
                .addMappings(m-> m.skip(Appointment::setDoctor)).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Appointment source, AppointmentDto destination) {
        long customerId = source.getCustomer().getId();
        long doctorId = source.getDoctor().getId();
        destination.setCustomerId(customerId);
        destination.setDoctorId(doctorId);
    }

    @Override
    public void mapSpecificFields(AppointmentDto source, Appointment destination) {
        long customerId = source.getCustomerId();
        long doctorId = source.getDoctorId();
        Customer customer = customerDao.find(customerId).orElseThrow(
                ()-> new MapperException("Customer with id " + customerId + " doesnt exist")
        );
        Doctor doctor = doctorDao.find(doctorId).orElseThrow(
                ()-> new MapperException("Doctor with id " + doctorId + "doesnt exist")
        );
        destination.setCustomer(customer);
        destination.setDoctor(doctor);
    }
}
