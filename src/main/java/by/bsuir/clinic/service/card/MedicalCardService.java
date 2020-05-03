package by.bsuir.clinic.service.card;

import by.bsuir.clinic.dto.MedicalCardDto;
import by.bsuir.clinic.model.MedicalCard;

import java.util.List;
import java.util.Optional;

public interface MedicalCardService {
    Optional<MedicalCardDto> findCardById(Long id);
    void save(MedicalCardDto medicalCardDto);
    List<MedicalCardDto> findAll();
    Optional<MedicalCardDto> update(MedicalCardDto medicalCardDto);
    void delete(Long id);
}
