package by.bsuir.clinic.dao.card;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.MedicalCard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MedicalCardDaoImpl extends AbstractCrudDao<MedicalCard> implements MedicalCardDao {

    public MedicalCardDaoImpl() {
        setClazz(MedicalCard.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<MedicalCard> findByCustomerId(long customerId) {
        Query query = entityManager.createQuery("From "
                + MedicalCard.class.getSimpleName()
                + " M where M.customer.id=:id");
        query.setParameter("id", customerId);
        List<MedicalCard> result = query.getResultList();
        return result.size()==0
                ? Optional.empty()
                : Optional.of(result.get(0));
    }
}
