package com.drobot.web.model.entity;

/**
 * Enumeration of patients' treatments available.
 *
 * @author Vladislav Drobot
 */
public enum Treatment {

    /**
     * Represents a procedure type of treatment.
     */
    PROCEDURE(1, true),

    /**
     * Represents a surgery type of treatment.
     */
    SURGERY(2, false);

    private final int treatmentId;
    private final boolean allowedForAssistants;

    Treatment(int treatmentId, boolean allowedForAssistants) {
        this.treatmentId = treatmentId;
        this.allowedForAssistants = allowedForAssistants;
    }

    /**
     * Getter method of treatment's ID.
     *
     * @return treatment's ID int value.
     */
    public int getTreatmentId() {
        return treatmentId;
    }

    /**
     * Getter method of a flag showing is a given treatment allowed for assistants.
     *
     * @return true if the treatment is allowed for assistants, false otherwise.
     */
    public boolean isAllowedForAssistants() {
        return allowedForAssistants;
    }
}
