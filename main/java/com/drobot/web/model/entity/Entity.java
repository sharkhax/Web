package com.drobot.web.model.entity;

public abstract class Entity {

    public enum Status {
        ACTIVE(0),
        BLOCKED(1),
        VACATION(2),
        ARCHIVE(3),
        WAITING_FOR_CURING(4),
        WAITING_FOR_DECISION(5),
        UNREMOVABLE(127);

        private final int statusId;

        Status(int statusId) {
            this.statusId = statusId;
        }

        public int getStatusId() {
            return statusId;
        }

        public static Status defineStatus(int statusId) { // FIXME: 10.11.2020 useless
            Status[] values = Status.values();
            Status result = null;
            for (Status status : values) {
                if (status.statusId == statusId) {
                    result = status;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("There is no status with id " + statusId);
            }
            return result;
        }
    }

    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public Entity() {
    }

    public int getId() {
        return id;
    }

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
