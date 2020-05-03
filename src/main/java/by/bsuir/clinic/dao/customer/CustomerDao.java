package by.bsuir.clinic.dao.customer;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.Customer;

import java.util.Optional;

public interface CustomerDao extends CrudDao<Customer> {
    Optional<Customer> findCustomerByUserId(Long userId);
}
