package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dao.customer.CustomerDao;
import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.MedicalCard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MedicalCardMapper extends AbstractMapper<MedicalCard, MedicalCardDto> {
    private final CustomerDao customerDao;
    private final ModelMapper mapper;

    @Autowired
    public MedicalCardMapper(CustomerDao customerDao,
                             ModelMapper mapper) {
        super(MedicalCard.class, MedicalCardDto.class);
        this.customerDao = customerDao;
        this.mapper = mapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(MedicalCard.class, MedicalCardDto.class)
                .addMappings(m -> m.skip(MedicalCardDto::setCustomerId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(MedicalCardDto.class, MedicalCard.class)
                .addMappings(m -> m.skip(MedicalCard::setCustomer)).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(MedicalCard source, MedicalCardDto destination) {
        destination.setCustomerId(source.getCustomer().getId());
    }


    @Override
    void mapSpecificFields(MedicalCardDto source, MedicalCard destination) {
        long customerId = source.getCustomerId();
        Customer customer = customerDao.find(customerId).orElseThrow(
                ()-> new MapperException("There is no customer with id : " + customerId)
        );
        destination.setCustomer(customer);

    }
}
