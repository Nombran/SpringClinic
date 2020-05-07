package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.appointment.AppointmentDao;
import by.bsuir.clinic.dao.card.MedicalCardDao;
import by.bsuir.clinic.dao.user.UserDao;
import by.bsuir.clinic.dto.AppointmentDto;
import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.model.Appointment;
import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.MedicalCard;
import by.bsuir.clinic.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DetailedCustomerMapper extends AbstractMapper<Customer, CustomerDto> {

    private final ModelMapper mapper;
    private final UserDao userDao;
    private final MedicalCardDao medicalCardDao;
    private final AppointmentDao appointmentDao;
    private final MedicalCardMapper medicalCardMapper;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public DetailedCustomerMapper(ModelMapper mapper,
                                  UserDao userDao,
                                  MedicalCardDao medicalCardDao,
                                  AppointmentDao appointmentDao,
                                  MedicalCardMapper medicalCardMapper,
                                  AppointmentMapper appointmentMapper) {
        super(Customer.class, CustomerDto.class);
        this.mapper = mapper;
        this.medicalCardDao = medicalCardDao;
        this.userDao = userDao;
        this.appointmentDao = appointmentDao;
        this.medicalCardMapper = medicalCardMapper;
        this.appointmentMapper = appointmentMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Customer.class, CustomerDto.class)
                .addMappings(m -> m.skip(CustomerDto::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(CustomerDto::setMedicalCardDto)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(CustomerDto::setAppointments)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(CustomerDto.class, Customer.class)
                .addMappings(m -> m.skip(Customer::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Customer::setMedicalCard)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Customer::setAppointments)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Customer source, CustomerDto destination) {
        long userId = source.getUser().getId();
        destination.setUserId(userId);
        MedicalCard medicalCard = source.getMedicalCard();
        if(medicalCard!=null) {
            destination.setMedicalCardDto(medicalCardMapper.toDto(medicalCard));
        }
        List<AppointmentDto> appointments = source.getAppointments()
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
        destination.setAppointments(appointments);
    }

    @Override
    void mapSpecificFields(CustomerDto source, Customer destination) {
        long userId = source.getUserId();
        User user = userDao.find(userId).orElseThrow(
                () -> new IllegalArgumentException("user with id : " + userId + "not found")
        );
        destination.setUser(user);
        if(source.getId() != null) {
            MedicalCard medicalCard = medicalCardDao.findByCustomerId(source.getId()).orElse(null);
            destination.setMedicalCard(medicalCard);
            destination.setAppointments(appointmentDao.findAppointmentByCustomerId(source.getId()));
        }
    }


}
