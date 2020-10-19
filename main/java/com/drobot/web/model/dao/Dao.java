package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<E extends Entity> extends CloseableDao {
    boolean add(E entity) throws DaoException;

    boolean remove(int id) throws DaoException;

    List<E> findAll(String sortBy) throws DaoException;

    Optional<E> findById(int id) throws DaoException;

    boolean update(E entity) throws DaoException;
}
