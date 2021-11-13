package com.alikmndlu.oqms.service.impl;

import com.alikmndlu.oqms.model.BaseModel;
import com.alikmndlu.oqms.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl <E extends BaseModel<PK>, PK extends Serializable, R extends JpaRepository<E, PK>>
        implements BaseService<E, PK> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E e) {
        return repository.save(e);
    }

    @Override
    public List<E> saveAll(Collection<E> collection) {
        return repository.saveAll(collection);
    }

    @Override
    public Optional<E> findById(PK id) {
        return repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(PK id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(E e) {
        repository.delete(e);
    }
}
