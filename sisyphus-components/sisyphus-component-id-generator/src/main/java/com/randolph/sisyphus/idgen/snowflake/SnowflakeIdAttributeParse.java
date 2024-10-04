package com.randolph.sisyphus.idgen.snowflake;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author : randolph
 * date : 2024/10/4 14:43
 */
public abstract class SnowflakeIdAttributeParse {
    public static final String DELIMITER = "-";
    protected final ZoneId zoneId;
    protected final long epoch;

    protected final int sequenceBitLength;
    protected final long sequenceMask;

    protected final int machineBitLength;
    protected final long machineMask;
    protected final int machineLeft;

    protected final int timestampBitLength;
    protected final long timestampMask;
    protected final int timestampLeft;

    public SnowflakeIdAttributeParse(long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength){
        this(ZoneId.systemDefault(), epoch,sequenceBitLength, machineBitLength, timestampBitLength);
    }

    public SnowflakeIdAttributeParse(ZoneId zoneId, long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        this.zoneId = zoneId;
        this.epoch = epoch;
        this.sequenceBitLength = sequenceBitLength;
        this.sequenceMask = getMask(sequenceBitLength);
        this.machineBitLength = machineBitLength;
        this.machineMask = getMask(machineBitLength);
        this.machineLeft = sequenceBitLength;
        this.timestampBitLength = timestampBitLength;
        this.timestampMask = getMask(timestampBitLength);
        this.timestampLeft = machineLeft + machineBitLength;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    protected abstract DateTimeFormatter getTimeFormatter();
    protected abstract LocalDateTime getTimestamp(long deltaTimestamp);
    protected abstract long deltaTimestamp();

    public SnowflakeIdAttribute parse(long id){
        LocalDateTime parseTimestamp = parseTimestamp(id);
        int parseMachineId = parseMachineId(id);
        long parseSequence = parseSequence(id);
        return SnowflakeIdAttribute.builder()
                .id(id)
                .timestamp(parseTimestamp)
                .machineId(parseMachineId)
                .sequence(parseSequence)
                .build();
    }

    public LocalDateTime parseTimestamp(long id){
        long deltaTimestamp = (id >> timestampLeft) & timestampMask;
        return getTimestamp(deltaTimestamp);
    }

    public int parseMachineId(long id){
        return (int) ((id >> machineLeft) & machineMask);
    }

    public long parseSequence(long id){
        return id & sequenceMask;
    }

    public long getMask(int bitLength) {
        return ~(-1L << bitLength);
    }


}
