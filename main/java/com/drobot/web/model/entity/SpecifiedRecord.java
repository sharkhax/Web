package com.drobot.web.model.entity;

public class SpecifiedRecord extends Entity {

    private String doctorName;
    private String doctorSurname;
    private Treatment treatment;
    private String executorName;
    private String executorSurname;
    private String diagnosis;

    public SpecifiedRecord(int id, String doctorName, String doctorSurname, Treatment treatment,
                           String executorName, String executorSurname, String diagnosis) {
        super(id);
        this.doctorName = doctorName;
        this.doctorSurname = doctorSurname;
        this.treatment = treatment;
        this.executorName = executorName;
        this.executorSurname = executorSurname;
        this.diagnosis = diagnosis;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getExecutorSurname() {
        return executorSurname;
    }

    public void setExecutorSurname(String executorSurname) {
        this.executorSurname = executorSurname;
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
        SpecifiedRecord that = (SpecifiedRecord) o;
        if (doctorName != null ? !doctorName.equals(that.doctorName) : that.doctorName != null) {
            return false;
        }
        if (doctorSurname != null ? !doctorSurname.equals(that.doctorSurname) : that.doctorSurname != null) {
            return false;
        }
        if (treatment != that.treatment) {
            return false;
        }
        if (executorName != null ? !executorName.equals(that.executorName) : that.executorName != null) {
            return false;
        }
        if (executorSurname != null ? !executorSurname.equals(that.executorSurname) : that.executorSurname != null) {
            return false;
        }
        return diagnosis != null ? diagnosis.equals(that.diagnosis) : that.diagnosis == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (doctorName != null ? doctorName.hashCode() : 0);
        result = 31 * result + (doctorSurname != null ? doctorSurname.hashCode() : 0);
        result = 31 * result + (treatment != null ? treatment.hashCode() : 0);
        result = 31 * result + (executorName != null ? executorName.hashCode() : 0);
        result = 31 * result + (executorSurname != null ? executorSurname.hashCode() : 0);
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SpecifiedRecord{");
        sb.append("id=").append(getId());
        sb.append(", doctorName='").append(doctorName).append('\'');
        sb.append(", doctorSurname='").append(doctorSurname).append('\'');
        sb.append(", treatment=").append(treatment);
        sb.append(", executorName='").append(executorName).append('\'');
        sb.append(", executorSurname='").append(executorSurname).append('\'');
        sb.append(", diagnosis='").append(diagnosis).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
