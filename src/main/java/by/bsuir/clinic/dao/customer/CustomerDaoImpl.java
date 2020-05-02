package by.bsuir.clinic.dao.customer;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomerDaoImpl extends AbstractCrudDao<Customer> implements CustomerDao {

    public CustomerDaoImpl() {
        setClazz(Customer.class);
    }

}
