package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.card.MedicalCardDao;
import by.bsuir.clinic.dao.user.UserDao;
import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.MedicalCard;
import by.bsuir.clinic.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomerMapper extends AbstractMapper<Customer, CustomerDto> {

    private final ModelMapper mapper;
    private final UserDao userDao;
    private final MedicalCardDao medicalCardDao;

    @Autowired
    public CustomerMapper(ModelMapper mapper,
                          UserDao userDao,
                          MedicalCardDao medicalCardDao) {
        super(Customer.class, CustomerDto.class);
        this.mapper = mapper;
        this.medicalCardDao = medicalCardDao;
        this.userDao = userDao;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Customer.class, CustomerDto.class)
                .addMappings(m -> m.skip(CustomerDto::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(CustomerDto::setMedicalCardId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(CustomerDto.class, Customer.class)
                .addMappings(m -> m.skip(Customer::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Customer::setMedicalCard)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Customer source, CustomerDto destination) {
        long userId = source.getUser().getId();
        destination.setUserId(userId);
        long medicalCardId = source.getMedicalCard().getId();
        destination.setMedicalCardId(medicalCardId);
    }

    @Override
    void mapSpecificFields(CustomerDto source, Customer destination) {
        long userId = source.getUserId();
        long medicalCardId = source.getMedicalCardId();
        User user = userDao.find(userId).orElseThrow(
                () -> new IllegalArgumentException("user with id : " + userId + "not found")
        );
        MedicalCard medicalCard = medicalCardDao.find(medicalCardId).orElseThrow(
                () -> new IllegalArgumentException("medical card with id : " + medicalCardId + "not found")
        );
        destination.setUser(user);
        destination.setMedicalCard(medicalCard);
    }


}
