package com.randolph.sisyphus.idgen.snowflake;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : randolph
 * date : 2024/10/4 14:41
 */
public class SnowflakeIdAttribute implements Comparable<SnowflakeIdAttribute> {

    private long id;

    private int machineId;

    private long sequence;

    private LocalDateTime timestamp;

    SnowflakeIdAttribute(long id, int machineId, long sequence, LocalDateTime timestamp){
        this.id = id;
        this.machineId = machineId;
        this.sequence = sequence;
        this.timestamp = timestamp;
    }

    private SnowflakeIdAttribute(SnowflakeIdAttributeBuilder snowflakeIdAttributeBuilder) {
        id = snowflakeIdAttributeBuilder.id;
        machineId = snowflakeIdAttributeBuilder.machineId;
        sequence = snowflakeIdAttributeBuilder.sequence;
        timestamp = snowflakeIdAttributeBuilder.timestamp;
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

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @apiNote In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * The string output is not necessarily stable over time or across
     * JVM invocations.
     * @implSpec The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(SnowflakeIdAttribute other) {
        return Long.compare(this.id, other.id);
    }


    public static class SnowflakeIdAttributeBuilder {
        private long id;
        private int machineId;
        private long sequence;
        private LocalDateTime timestamp;

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

        public SnowflakeIdAttribute build() {
            return new SnowflakeIdAttribute(id, machineId, sequence, timestamp);
        }

        @Override
        public String toString() {
            return "SnowflakeIdAttribute.Builder{" +
                    "id=" + this.id +
                    ", machineId=" + this.machineId +
                    ", sequence=" + this.sequence +
                    ", timestamp=" + this.timestamp +
                    '}';
        }
    }
}
