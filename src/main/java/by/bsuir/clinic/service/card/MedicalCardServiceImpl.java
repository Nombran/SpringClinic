package by.bsuir.clinic.service.card;

import by.bsuir.clinic.dao.card.MedicalCardDao;
import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.mapper.MedicalCardMapper;
import by.bsuir.clinic.model.MedicalCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalCardServiceImpl implements MedicalCardService {

    private final MedicalCardDao dao;
    private final MedicalCardMapper mapper;

    @Autowired
    public MedicalCardServiceImpl(MedicalCardDao dao,
                                  MedicalCardMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }


    @Override
    public Optional<MedicalCardDto> findCardById(Long id) {
        return dao.find(id).map(mapper::toDto);
    }

    @Override
    public void save(MedicalCardDto medicalCardDto) {
        dao.save(mapper.toEntity(medicalCardDto));
    }

    @Override
    public List<MedicalCardDto> findAll() {
        return dao.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MedicalCardDto> update(MedicalCardDto medicalCardDto) {
        MedicalCard updated = dao.update(mapper.toEntity(medicalCardDto));
        return updated != null
                ? Optional.ofNullable(mapper.toDto(updated))
                : Optional.empty();
    }

    @Override
    public void delete(Long id) {
        MedicalCard forDelete = dao.find(id).orElseThrow(IllegalArgumentException::new);
        dao.delete(forDelete);
    }
}
