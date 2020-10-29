package com.drobot.web.model.entity;

public class PatientRecord extends Entity {

    private int patientId;
    private int doctorId;
    private int curingId;
    private String diagnosis;

    public PatientRecord() {
    }

    public PatientRecord(int id, int patientId, int doctorId, int curingId, String diagnosis) {
        super(id);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.curingId = curingId;
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

    public int getCuringId() {
        return curingId;
    }

    public void setCuringId(int curingId) {
        this.curingId = curingId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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
        PatientRecord patientRecord = (PatientRecord) o;
        if (patientId != patientRecord.patientId) {
            return false;
        }
        if (doctorId != patientRecord.doctorId) {
            return false;
        }
        if (!diagnosis.equals(patientRecord.diagnosis)) {
            return false;
        }
        return curingId == patientRecord.curingId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + patientId;
        result = 31 * result + doctorId;
        result = 31 * result + curingId;
        result = 31 * result + diagnosis.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatientRecord{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", patientId=").append(patientId);
        sb.append(", doctorId=").append(doctorId);
        sb.append(", curingId=").append(curingId);
        sb.append(", diagnosis=").append(diagnosis);
        sb.append('}');
        return sb.toString();
    }
}
