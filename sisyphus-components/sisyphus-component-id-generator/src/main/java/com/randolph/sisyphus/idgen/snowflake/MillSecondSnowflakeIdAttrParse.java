package com.randolph.sisyphus.idgen.snowflake;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

/**
 * @author : randolph
 * date : 2024/10/4 15:20
 */
public class MillSecondSnowflakeIdAttrParse extends SnowflakeIdAttributeParse {

    public static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendValue(ChronoField.MILLI_OF_SECOND, 3)
            .toFormatter();

    public MillSecondSnowflakeIdAttrParse(long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        this(ZoneId.systemDefault(), epoch, sequenceBitLength, machineBitLength, timestampBitLength);
    }

    public MillSecondSnowflakeIdAttrParse(ZoneId zoneId, long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
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
    protected long deltaTimestamp() {
        return 0;
    }
}
