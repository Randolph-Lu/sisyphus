package com.randolph.sisyphus.idgen.snowflake;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : randolph
 * date : 2024/10/4 14:41
 */
public class SnowflakeIdAttribute implements Comparable<SnowflakeIdAttribute> {

    private final long id;

    private final int machineId;

    private final long sequence;

    private final LocalDateTime timestamp;

    private final String readableSnowflakeId;

    SnowflakeIdAttribute(long id, int machineId, long sequence, LocalDateTime timestamp, String readableSnowflakeId){
        this.id = id;
        this.machineId = machineId;
        this.sequence = sequence;
        this.timestamp = timestamp;
        this.readableSnowflakeId = readableSnowflakeId;
    }

    public static SnowflakeIdAttributeBuilder builder() {
        return new SnowflakeIdAttributeBuilder();
    }


    public long getId() {
        return id;
    }

    public int getMachineId() {
        return machineId;
    }

    public long getSequence() {
        return sequence;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReadableSnowflakeId(){
        return readableSnowflakeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (Objects.isNull(obj) || !getClass().isInstance(obj)){
            return false;
        }

        SnowflakeIdAttribute that = (SnowflakeIdAttribute) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return getReadableSnowflakeId();
    }

    @Override
    public int compareTo(SnowflakeIdAttribute other) {
        return Long.compare(this.id, other.id);
    }


    public static class SnowflakeIdAttributeBuilder {
        private long id;
        private int machineId;
        private long sequence;
        private LocalDateTime timestamp;

        private String readableSnowflakeId;

        public SnowflakeIdAttributeBuilder() {

        }

        public SnowflakeIdAttributeBuilder id(long val) {
            id = val;
            return this;
        }

        public SnowflakeIdAttributeBuilder machineId(int val) {
            machineId = val;
            return this;
        }

        public SnowflakeIdAttributeBuilder sequence(long val) {
            sequence = val;
            return this;
        }

        public SnowflakeIdAttributeBuilder timestamp(LocalDateTime val) {
            timestamp = val;
            return this;
        }

        public SnowflakeIdAttributeBuilder readableSnowflakeId(String val){
            readableSnowflakeId = val;
            return this;
        }

        public SnowflakeIdAttribute build() {
            return new SnowflakeIdAttribute(id, machineId, sequence, timestamp, readableSnowflakeId);
        }

        @Override
        public String toString() {
            return "SnowflakeIdAttribute.Builder{" +
                    "id=" + this.id +
                    ", machineId=" + this.machineId +
                    ", sequence=" + this.sequence +
                    ", timestamp=" + this.timestamp +
                    ", readableSnowflakeId" + this.readableSnowflakeId +
                    '}';
        }
    }
}
