package com.drobot.web.model.entity;

/**
 * Entity class of a patient record.
 *
 * @author Vladislav Drobot
 */
public class PatientRecord extends Entity {

    private int patientId;
    private int doctorId;
    private Treatment treatment;
    private int executorId;
    private String diagnosis;

    /**
     * Constructs a PatientRecord object.
     */
    public PatientRecord() {
    }

    /**
     * Constructs a PatientRecord object with given patient ID, doctor ID, treatment, diagnosis.
     *
     * @param patientId patient's ID int value.
     * @param doctorId  doctor's ID int value.
     * @param treatment Treatment object of patient's treatment.
     * @param diagnosis String object of patient's diagnosis.
     */
    public PatientRecord(int patientId, int doctorId, Treatment treatment, String diagnosis) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatment = treatment;
        this.diagnosis = diagnosis;
    }

    /**
     * Constructs a PatientRecord object with given record ID, patient ID, doctor ID, treatment, executor ID,
     * diagnosis.
     *
     * @param recordId   record's ID int value.
     * @param patientId  patient's ID int value.
     * @param doctorId   doctor's ID int value.
     * @param treatment  Treatment object of patient's treatment.
     * @param executorId executor's ID int value.
     * @param diagnosis  String object of patient's diagnosis.
     */
    public PatientRecord(int recordId, int patientId, int doctorId, Treatment treatment,
                         int executorId, String diagnosis) {
        super(recordId);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatment = treatment;
        this.executorId = executorId;
        this.diagnosis = diagnosis;
    }

    /**
     * Getter method of patient's ID.
     *
     * @return patient's ID int value.
     */
    public int getPatientId() {
        return patientId;
    }

    /**
     * Setter method of patient's ID.
     *
     * @param patientId patient's ID int value.
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Getter method of doctor's ID.
     *
     * @return doctor's ID int value.
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * Setter method of doctor's ID.
     *
     * @param doctorId doctor's ID int value.
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Getter method of patient's treatment.
     *
     * @return Treatment object of patient's treatment.
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     * Setter method of patient's treatment.
     *
     * @param treatment Treatment object of patient's treatment.
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    /**
     * Getter method of patient's diagnosis.
     *
     * @return String object of patient's diagnosis.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Setter method of patient's treatment.
     *
     * @param diagnosis String object of patient's diagnosis.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Getter method of executor's ID.
     *
     * @return executor's ID int value.
     */
    public int getExecutorId() {
        return executorId;
    }

    /**
     * Setter method of executor's ID.
     *
     * @param executorId executor's ID int value.
     */
    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PatientRecord record = (PatientRecord) o;
        if (patientId != record.patientId) {
            return false;
        }
        if (doctorId != record.doctorId) {
            return false;
        }
        if (treatment != record.treatment) {
            return false;
        }
        if (executorId != record.executorId) {
            return false;
        }
        return diagnosis != null ? diagnosis.equals(record.diagnosis) : record.diagnosis == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + patientId;
        result = 31 * result + doctorId;
        result = 31 * result + treatment.hashCode();
        result = 31 * result + executorId;
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientRecord{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", patientId=").append(patientId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", treatment=").append(treatment);
        sb.append(", executorId=").append(executorId);
        sb.append(", diagnosis=").append(diagnosis);
        sb.append('}');
        return sb.toString();
    }
}
