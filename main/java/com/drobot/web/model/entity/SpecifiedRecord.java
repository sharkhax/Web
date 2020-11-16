package com.drobot.web.model.entity;

/**
 * Entity class of a specified record.
 *
 * @author Vladislav Drobot
 */
public class SpecifiedRecord extends Entity {

    private String doctorName;
    private String doctorSurname;
    private Treatment treatment;
    private String executorName;
    private String executorSurname;
    private String diagnosis;

    /**
     * Constructs a SpecifiedRecord object with given record ID, doctor name, doctor surname, treatment, executor name,
     * executor surname, diagnosis.
     *
     * @param recordId        record's ID int value.
     * @param doctorName      String object of doctor's name.
     * @param doctorSurname   String object of doctor's surname.
     * @param treatment       Treatment object of patient's treatment.
     * @param executorName    String object of executor's name.
     * @param executorSurname String object of executor's surname.
     * @param diagnosis       String object of patient's diagnosis.
     */
    public SpecifiedRecord(int recordId, String doctorName, String doctorSurname, Treatment treatment,
                           String executorName, String executorSurname, String diagnosis) {
        super(recordId);
        this.doctorName = doctorName;
        this.doctorSurname = doctorSurname;
        this.treatment = treatment;
        this.executorName = executorName;
        this.executorSurname = executorSurname;
        this.diagnosis = diagnosis;
    }

    /**
     * Getter method of doctor's name.
     *
     * @return String object of doctor's name.
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Setter method of doctor's name.
     *
     * @param doctorName String object of doctor's name.
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * Getter method of doctor's surname.
     *
     * @return String object of doctor's surname.
     */
    public String getDoctorSurname() {
        return doctorSurname;
    }

    /**
     * Setter method of doctor's surname.
     *
     * @param doctorSurname String object of doctor's surname.
     */
    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
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
     * Getter method of executor's name.
     *
     * @return String object of executor's name.
     */
    public String getExecutorName() {
        return executorName;
    }

    /**
     * Setter method of executor's name.
     *
     * @param executorName String object of executor's name.
     */
    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    /**
     * Getter method of executor's surname.
     *
     * @return String object of executor's surname.
     */
    public String getExecutorSurname() {
        return executorSurname;
    }

    /**
     * Setter method of executor's surname.
     *
     * @param executorSurname String object of executor's surname.
     */
    public void setExecutorSurname(String executorSurname) {
        this.executorSurname = executorSurname;
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
