package com.randolph.sisyphus.idgen.snowflake;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author : randolph
 * date : 2024/10/4 15:21
 */
public class SecondSnowflakeIdAttrParse extends SnowflakeIdAttributeParse{
    public SecondSnowflakeIdAttrParse(long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        this(ZoneId.systemDefault(),epoch, sequenceBitLength, machineBitLength, timestampBitLength);
    }

    public SecondSnowflakeIdAttrParse(ZoneId zoneId, long epoch, int sequenceBitLength, int machineBitLength, int timestampBitLength) {
        super(zoneId, epoch, sequenceBitLength, machineBitLength, timestampBitLength);
    }

    @Override
    protected DateTimeFormatter getTimeFormatter() {
        return null;
    }

    @Override
    protected LocalDateTime getTimestamp(long deltaTimestamp) {
        return null;
    }

    @Override
    protected long deltaTimestamp() {
        return 0;
    }
}
