package com.drobot.web.model.service;

import java.util.Map;

/**
 * Interface for actions on Map objects containing entities inside.
 *
 * @author Vladislav Drobot
 */
public interface MapService {

    /**
     * Checks if Map object, containing entity's fields with RequestParameter constants as keys, is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if the object is valid, false otherwise.
     */
    boolean isMapValid(Map<String, String> fields);
}
