package com.randolph.sisyphus.idgen.snowflake;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

/**
 * @author : randolph
 * date : 2024/10/4 15:21
 */
public class SecondSnowflakeIdAttrParser extends SnowflakeIdAttributeParser {

    public static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter();

    public SecondSnowflakeIdAttrParser(long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        this(ZoneId.systemDefault(),epoch, sequenceBitLength, machineBitLength, timestampBitLength);
    }

    public SecondSnowflakeIdAttrParser(ZoneId zoneId, long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        super(zoneId, epoch, sequenceBitLength, machineBitLength, timestampBitLength);
    }

    @Override
    protected DateTimeFormatter getTimeFormatter() {
        return TIME_FORMATTER;
    }

    @Override
    protected LocalDateTime getTimestamp(long deltaTimestamp) {
        return Instant.ofEpochMilli(TimeUnit.MILLISECONDS.toMillis(deltaTimestamp + epoch)).atZone(getZoneId()).toLocalDateTime();
    }

    @Override
    protected long deltaTimestamp(LocalDateTime deltaTimestamp) {
        return ZonedDateTime.of(deltaTimestamp, getZoneId()).toInstant().getEpochSecond()- epoch;
    }

    static SecondSnowflakeIdAttrParser of(SnowflakeId generator){
        return of(generator, ZoneId.systemDefault());
    }

    static SecondSnowflakeIdAttrParser of(SnowflakeId generator, ZoneId zoneId){
        return new SecondSnowflakeIdAttrParser(zoneId, generator.getEpoch(), generator.getSequenceBitLength(), generator.getMachineIdBitLength(), generator.getTimestampBitLength());
    }

}
