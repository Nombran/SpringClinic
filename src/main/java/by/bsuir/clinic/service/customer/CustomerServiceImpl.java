package by.bsuir.clinic.service.customer;

import by.bsuir.clinic.dao.appointment.AppointmentDao;
import by.bsuir.clinic.dao.card.MedicalCardDao;
import by.bsuir.clinic.dao.customer.CustomerDao;
import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.dto.TicketForCustomerDto;
import by.bsuir.clinic.mapper.CustomerMapper;
import by.bsuir.clinic.mapper.DetailedCustomerMapper;
import by.bsuir.clinic.mapper.MedicalCardMapper;
import by.bsuir.clinic.mapper.TicketForCustomerMapper;
import by.bsuir.clinic.model.Customer;
import by.bsuir.clinic.model.MedicalCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final DetailedCustomerMapper detailedCustomerMapper;
    private final CustomerMapper customerMapper;
    private final MedicalCardMapper cardMapper;
    private final MedicalCardDao medicalCardDao;
    private final AppointmentDao appointmentDao;
    private final TicketForCustomerMapper ticketForCustomerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao,
                               DetailedCustomerMapper detailedCustomerMapper,
                               MedicalCardMapper medicalCardMapper,
                               MedicalCardDao medicalCardDao,
                               CustomerMapper customerMapper,
                               AppointmentDao appointmentDao,
                               TicketForCustomerMapper ticketForCustomerMapper) {
        this.customerDao = customerDao;
        this.detailedCustomerMapper = detailedCustomerMapper;
        this.cardMapper = medicalCardMapper;
        this.medicalCardDao = medicalCardDao;
        this.customerMapper = customerMapper;
        this.appointmentDao = appointmentDao;
        this.ticketForCustomerMapper = ticketForCustomerMapper;
    }

    @Override
    public Optional<CustomerDto> findCustomerById(Long id) {
        return customerDao.find(id).map(detailedCustomerMapper::toDto);
    }

    @Override
    public void save(CustomerDto customerDto) {
        customerDao.save(detailedCustomerMapper.toEntity(customerDto));
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerDao.findAll()
                .stream()
                .map(customer -> {
                    CustomerDto customerDto = customerMapper.toDto(customer);
                    customerDto.setAppointments(null);
                    customerDto.setMedicalCard(null);
                    return customerDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> update(CustomerDto customerDto) {
        Customer customer = detailedCustomerMapper.toEntity(customerDto);
        customer = customerDao.update(customer);
        if(customer != null) {
            return Optional.ofNullable(customerMapper.toDto(customer));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerDao.find(id).orElseThrow(
                ()-> new IllegalArgumentException("customer with id : " + id + "not found")
        );
        customerDao.delete(customer);
    }

    @Override
    public Optional<CustomerDto> findCustomerByUserId(Long userId) {
        Optional<Customer> result = customerDao.findCustomerByUserId(userId);
        return result.isPresent() ? result.map(detailedCustomerMapper::toDto) : Optional.empty();
    }

    @Override
    public Optional<MedicalCardDto> getMedicalCard(long customerId) {
        Customer customer = customerDao.find(customerId).orElseThrow(
                ()-> new IllegalArgumentException("Customer with id : "+ customerId + " not exists")
        );
        MedicalCard medicalCard = customer.getMedicalCard();
        if(medicalCard != null) {
            MedicalCardDto dto = cardMapper.toDto(medicalCard);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void setMedicalCard(MedicalCardDto medicalCard, long customerId) {
        Customer customer = customerDao.find(customerId).orElseThrow(
                ()-> new IllegalArgumentException("Customer with id : "+ customerId + " not exists")
        );
        medicalCard.setCustomerId(customerId);
        MedicalCard medicalCardEntity = cardMapper.toEntity(medicalCard);
        medicalCardDao.save(medicalCardEntity);
        customer.setMedicalCard(medicalCardEntity);
    }

    @Override
    public Optional<MedicalCardDto> updateCustomerMedicalCard(MedicalCardDto medicalCardDto, long customerId) {
        Customer customer = customerDao.find(customerId).orElseThrow(
                ()-> new IllegalArgumentException("Customer with id : "+ customerId + " not exists")
        );
        MedicalCard medicalCardBeforeUpdate = customer.getMedicalCard();
        if(medicalCardBeforeUpdate == null) {
            return Optional.empty();
        } else {
            long medicalCardId = medicalCardBeforeUpdate.getId();
            medicalCardDto.setId(medicalCardId);
            medicalCardDto.setCustomerId(customerId);
            MedicalCard medicalCardForUpdate = cardMapper.toEntity(medicalCardDto);
            MedicalCard updated = medicalCardDao.update(medicalCardForUpdate);
            if(updated != null) {
                return Optional.of(cardMapper.toDto(updated));
            } else {
                return Optional.empty();
            }
        }

    }

    @Override
    public List<TicketForCustomerDto> getCustomerTickets(long customerId) {
        return appointmentDao.findAppointmentByCustomerId(customerId)
                .stream()
                .map(ticketForCustomerMapper::toDto)
                .collect(Collectors.toList());
    }


}
