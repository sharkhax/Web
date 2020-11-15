package com.drobot.web.model.entity;

public class PatientRecord extends Entity {

    private int patientId;
    private int doctorId;
    private Treatment treatment;
    private int executorId;
    private String diagnosis;

    public PatientRecord() {
    }

    public PatientRecord(int patientId, int doctorId, Treatment treatment, String diagnosis) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatment = treatment;
        this.diagnosis = diagnosis;
    }

    public PatientRecord(int recordId, int patientId, int doctorId, Treatment treatment,
                         int executorId, String diagnosis) {
        super(recordId);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.treatment = treatment;
        this.executorId = executorId;
        this.diagnosis = diagnosis;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getExecutorId() {
        return executorId;
    }

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
