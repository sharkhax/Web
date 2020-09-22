package com.drobot.web.model.entity;

public abstract class Entity {

    public enum Status {
        ACTIVE((byte) 0),
        BLOCKED((byte) 1),
        VACATION((byte) 2),
        ARCHIVE((byte) 3),
        WAITING_FOR_CURING((byte) 4),
        WAITING_FOR_DECISION((byte) 5),
        UNREMOVABLE((byte) 127);

        private final byte statusId;

        Status(byte statusId) {
            this.statusId = statusId;
        }

        public byte getStatusId() {
            return statusId;
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
