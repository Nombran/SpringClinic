package by.bsuir.clinic.dao.customer;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerDaoImpl extends AbstractCrudDao<Customer> implements CustomerDao {

    public CustomerDaoImpl() {
        setClazz(Customer.class);
    }


    @Override
    public Optional<Customer> findCustomerByUserId(Long userId) {
        Query query = entityManager.createQuery("From "
                + Customer.class.getSimpleName()
                + " C where C.user.id=:id");
        query.setParameter("id", userId);
        List result = query.getResultList();
        Optional<Customer> customer = result.size() == 0
                ? Optional.empty()
                : Optional.ofNullable((Customer)result.get(0));
        return customer;
    }
}
