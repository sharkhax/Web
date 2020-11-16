package com.drobot.web.model.entity;

/**
 * Abstract entity class.
 *
 * @author Vladislav Drobot
 */
public abstract class Entity {

    /**
     * Enumeration of entities' statuses.
     *
     * @author Vladislav Drobot
     */
    public enum Status {

        /**
         * Represents 'active' status.
         */
        ACTIVE(0),

        /**
         * Represents 'blocked' status.
         */
        BLOCKED(1),

        /**
         * Represents 'vacation' status.
         */
        VACATION(2),

        /**
         * Represents 'archive' status.
         */
        ARCHIVE(3),

        /**
         * Represents 'waiting_for_curing' status.
         */
        WAITING_FOR_CURING(4),

        /**
         * Represents 'waiting_for_decision' status.
         */
        WAITING_FOR_DECISION(5),

        /**
         * Represents 'unremovable' status.
         */
        UNREMOVABLE(127);

        private final int statusId;

        Status(int statusId) {
            this.statusId = statusId;
        }

        /**
         * Getter method of status's ID.
         *
         * @return status's ID int value.
         */
        public int getStatusId() {
            return statusId;
        }
    }

    private int id;

    /**
     * Constructs an Entity object.
     */
    public Entity() {
    }

    /**
     * Constructs an Entity object with a given ID.
     *
     * @param id int value of entity's ID.
     */
    public Entity(int id) {
        this.id = id;
    }

    /**
     * Getter method of entity's ID.
     *
     * @return entity's ID int value.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method of entity's ID.
     *
     * @param id entity's ID int value.
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
