package com.drobot.web.model.entity;

public enum Treatment {

    PROCEDURE(1, true),
    SURGERY(2, false);

    private final int treatmentId;
    private final boolean allowedForAssistants;

    Treatment(int treatmentId, boolean allowedForAssistants) {
        this.treatmentId = treatmentId;
        this.allowedForAssistants = allowedForAssistants;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public boolean isAllowedForAssistants() {
        return allowedForAssistants;
    }
}
