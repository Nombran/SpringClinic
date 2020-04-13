package by.bsuir.clinic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class AbstractCrudDao<T> implements CrudDao<T> {

    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public void setClazz(Class<T> classToSet) {
        clazz = classToSet;
    }

    @Override
    public void save(T t) {
        entityManager.persist(t);
    }

    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    @Override
    public Optional<T> find(long id) {
        return Optional.ofNullable(entityManager.find(clazz,id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return entityManager.createQuery("From " + clazz.getName())
                .getResultList();
    }
}
