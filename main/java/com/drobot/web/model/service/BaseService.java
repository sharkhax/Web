package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Base service interface provides actions on entities.
 *
 * @param <E> type of a given entity.
 * @author Vladislav Drobot
 */
public interface BaseService<E> {

    /**
     * Checks if the input parameters are valid, and then, if they are, finds all entities in a datasource
     * in a given border, sorts them by a given tag.
     *
     * @param start   int value of which row it should start the finding.
     * @param end     int value of which row it should end the finding.
     * @param sortBy  String representation of sorting tag.
     * @param reverse boolean flag if the list should be reserved.
     * @return List of entities if the data has been found, empty List object otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    List<E> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, finds the entity in a database by a given ID.
     *
     * @param id entity's ID int value.
     * @return Not empty Optional entity object if it was found, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<E> findById(int id) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, checks if the entity with a given ID
     * exists in a datasource.
     *
     * @param id entity's ID int value.
     * @return true if it was found, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean exists(int id) throws ServiceException;

    /**
     * Counts a total number of entities in a datasource.
     *
     * @return int value of the total number of objects.
     * @throws ServiceException if an error occurs while processing.
     */
    int count() throws ServiceException;

    /**
     * Packs a given entity into a Map with RequestParameter's constant keys.
     *
     * @param e entity object to be packed.
     * @return Map object with entity's fields with RequestParameter's constants as keys inside.
     */
    Map<String, String> packIntoMap(E e);

    /**
     * Check new fields if they are valid and if they don't exist. Then updates an entity in a datasource.
     *
     * @param newFields      Map object with new entity's fields with RequestParameter's constant keys inside.
     * @param existingFields empty Map object for existing entity's fields. They will be put there.
     * @param currentFields  Map object with current entity's fields with RequestParameter's constants as keys inside.
     * @return true if entity has been updated, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                   Map<String, String> currentFields) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, finds entity's status in a datasource.
     *
     * @param id entity's ID int value.
     * @return Not empty Optional Entity.Status object if it was found, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Entity.Status> findStatus(int id) throws ServiceException;
}
