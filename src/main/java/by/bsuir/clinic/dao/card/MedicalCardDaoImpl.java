package by.bsuir.clinic.dao.card;

import by.bsuir.clinic.dao.AbstractCrudDao;
import by.bsuir.clinic.model.MedicalCard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MedicalCardDaoImpl extends AbstractCrudDao<MedicalCard> implements MedicalCardDao {

    public MedicalCardDaoImpl() {
        setClazz(MedicalCard.class);
    }
}
