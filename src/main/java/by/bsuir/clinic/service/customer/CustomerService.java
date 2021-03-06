package by.bsuir.clinic.service.customer;

import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.dto.TicketForCustomerDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<CustomerDto> findCustomerById(Long id);
    void save(CustomerDto customerDto);
    List<CustomerDto> findAll();
    Optional<CustomerDto> update(CustomerDto customerDto);
    void delete(Long id);
    Optional<CustomerDto> findCustomerByUserId(Long userId);
    Optional<MedicalCardDto> getMedicalCard(long customerId);
    void setMedicalCard(MedicalCardDto medicalCard, long customerId);
    Optional<MedicalCardDto> updateCustomerMedicalCard(MedicalCardDto medicalCardDto, long customerId);
    List<TicketForCustomerDto> getCustomerTickets(long customerId);
}
