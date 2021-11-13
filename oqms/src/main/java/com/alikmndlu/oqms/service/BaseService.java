package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.BaseModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseModel<PK>, PK extends Serializable> {

    E save(E e);

    List<E> saveAll(Collection<E> collection);

    Optional<E> findById(PK id);

    List<E> findAll();

    void deleteById(PK id);

    void delete(E e);
}
