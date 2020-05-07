package by.bsuir.clinic.dao.card;

import by.bsuir.clinic.dao.CrudDao;
import by.bsuir.clinic.model.MedicalCard;

import java.util.Optional;

public interface MedicalCardDao extends CrudDao<MedicalCard> {
    Optional<MedicalCard> findByCustomerId(long customerId);
}
