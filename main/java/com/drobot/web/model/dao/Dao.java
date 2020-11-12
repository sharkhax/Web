package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<E extends Entity> extends CloseableDao {
    String SPACE = " ";
    String SEMICOLON = ";";
    String DESC = "DESC";

    boolean add(E entity) throws DaoException;

    List<E> findAll(String sortBy, boolean reverse) throws DaoException;

    List<E> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException;

    Optional<E> findById(int id) throws DaoException;

    boolean exists(int id) throws DaoException;

    int count() throws DaoException;
}
