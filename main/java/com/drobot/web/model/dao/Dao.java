package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Base Dao interface used for interactions with database tables.
 *
 * @param <E> The type of entity to work with.
 * @author Vladislav Drobot
 */
public interface Dao<E extends Entity> extends CloseableDao {

    /**
     * Represents a single space symbol. Used for constructing SQL statements.
     */
    String SPACE = " ";

    /**
     * Represents a string 'DESC'. Used for constructing SQL statements.
     */
    String DESC = "DESC";

    /**
     * Represents an asterisk symbol. Used for constructing SQL statements.
     */
    String ASTERISK = "*";

    /**
     * Finds all rows in the table in a given border, sorts them by a given tag.
     *
     * @param start   int value of which row it should start the finding.
     * @param end     int value of which row it should end the finding.
     * @param sortBy  String representation of sorting tag.
     * @param reverse boolean flag if the list should be reserved.
     * @return List of entities if the data has been found, empty List object otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    List<E> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException;

    /**
     * Finds an entity by its ID.
     *
     * @param id entity's ID int value.
     * @return Not empty Optional entity object if it was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<E> findById(int id) throws DaoException;

    /**
     * Checks if the entity with a given ID exists.
     *
     * @param id entity's ID int value.
     * @return true if it was found, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean exists(int id) throws DaoException;

    /**
     * Counts a total number of rows in the table.
     *
     * @return int value of the total number of rows.
     * @throws DaoException if the database throws SQLException.
     */
    int count() throws DaoException;
}
