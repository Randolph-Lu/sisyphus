package com.randolph.sisyphus.idgen.snowflake;

import com.google.common.base.Splitter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @author : randolph
 * date : 2024/10/4 14:43
 */
public abstract class SnowflakeIdAttributeParser {
    public static final String DELIMITER = "_";
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

    protected SnowflakeIdAttributeParser(long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength){
        this(ZoneId.systemDefault(), epoch,sequenceBitLength, machineBitLength, timestampBitLength);
    }

    protected SnowflakeIdAttributeParser(ZoneId zoneId, long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
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
    protected abstract long deltaTimestamp(LocalDateTime deltaTimestamp);

    public SnowflakeIdAttribute parse(long id){
        LocalDateTime parseTimestamp = parseTimestamp(id);
        int parseMachineId = parseMachineId(id);
        long parseSequence = parseSequence(id);

        String readableId = parseTimestamp.format(getTimeFormatter()) + DELIMITER + parseMachineId + DELIMITER + parseSequence;

        return SnowflakeIdAttribute.builder()
                .id(id)
                .timestamp(parseTimestamp)
                .machineId(parseMachineId)
                .sequence(parseSequence)
                .readableSnowflakeId(readableId)
                .build();
    }

    public SnowflakeIdAttribute parse(String idString) {
        Objects.requireNonNull(idString, "Readable ID can't be null");
        // 拆分字符串
        List<String> split = Splitter.on(DELIMITER).omitEmptyStrings().splitToList(idString);
        if (split.size() != 3){
            throw new IllegalArgumentException(String.format("Readable ID :[ %s ]. Unsupported type, please check the entered parameters.", idString));
        }
        // 转换各部分参数，将其转换到默认形式
        LocalDateTime timestamp = LocalDateTime.parse(split.get(0), getTimeFormatter());
        long parseMachineId = Long.parseLong(split.get(1));
        long parseSequence = Long.parseLong(split.get(2));
        long asLong = deltaTimestamp(timestamp) << timestampLeft | parseMachineId << machineLeft | parseSequence;

        return SnowflakeIdAttribute.builder()
                .id(asLong)
                .timestamp(timestamp)
                .machineId((int) parseMachineId)
                .sequence(parseSequence)
                .readableSnowflakeId(idString)
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

    static SnowflakeIdAttributeParser create(SnowflakeId generator){
        return create(generator, ZoneId.systemDefault());
    }

    static SnowflakeIdAttributeParser create(SnowflakeId generator, ZoneId zoneId){
        if (generator instanceof SecondSnowflakeId){
            return SecondSnowflakeIdAttrParser.of(generator, zoneId);
        }
        return MillSecondSnowflakeIdAttrParser.of(generator, zoneId);
    }

}
