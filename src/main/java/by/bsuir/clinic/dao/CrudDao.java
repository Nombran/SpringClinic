package by.bsuir.clinic.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    void save(T t);

    T update(T t);

    void delete(T t);

    Optional<T> find(long id);

    List<T> findAll();
}
