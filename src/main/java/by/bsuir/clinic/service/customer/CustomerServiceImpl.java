package by.bsuir.clinic.service.customer;

import by.bsuir.clinic.dao.customer.CustomerDao;
import by.bsuir.clinic.dto.CustomerDto;
import by.bsuir.clinic.mapper.CustomerMapper;
import by.bsuir.clinic.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao dao;
    private CustomerMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerDao dao,
                               CustomerMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Optional<CustomerDto> findCustomerById(Long id) {
        return dao.find(id).map(mapper::toDto);
    }

    @Override
    public void save(CustomerDto customerDto) {
        dao.save(mapper.toEntity(customerDto));
    }

    @Override
    public List<CustomerDto> findAll() {
        return dao.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> update(CustomerDto customerDto) {
        Customer customer = mapper.toEntity(customerDto);
        customer = dao.update(customer);
        if(customer != null) {
            return Optional.ofNullable(mapper.toDto(customer));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = dao.find(id).orElseThrow(
                ()-> new IllegalArgumentException("customer with id : " + id + "not found")
        );
        dao.delete(customer);
    }

    @Override
    public Optional<CustomerDto> findCustomerByUserId(Long userId) {
        Optional<Customer> result = dao.findCustomerByUserId(userId);
        return result.isPresent() ? result.map(mapper::toDto) : Optional.empty();
    }


}
