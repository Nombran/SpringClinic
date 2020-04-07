package by.bsuir.clinic.mapper;

import by.bsuir.clinic.dto.AbstractDto;
import by.bsuir.clinic.model.BaseEntity;

public interface Mapper<E extends BaseEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
