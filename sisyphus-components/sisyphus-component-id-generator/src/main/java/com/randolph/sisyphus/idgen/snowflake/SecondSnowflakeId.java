package com.randolph.sisyphus.idgen.snowflake;

import java.util.concurrent.TimeUnit;

/**
 * @author : randolph
 * date : 2024/10/2 16:58
 */
public class SecondSnowflakeId extends AbstractSnowflakeId {

    public static final byte DEFAULT_TIMESTAMP_BIT_LENGTH = 31;

    public static final byte DEFAULT_MACHINE_ID_BIT_LENGTH = 10;

    public static final byte DEFAULT_SEQUENCE_BIT_LENGTH = 22;

    public SecondSnowflakeId(long epoch, byte timestampBitLength, byte machineIdBitLength, byte sequenceBitLength, long machineId) {
        super(epoch, timestampBitLength, machineIdBitLength, sequenceBitLength, machineId);
    }

    @Override
    protected long getCurrentTime() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
}
