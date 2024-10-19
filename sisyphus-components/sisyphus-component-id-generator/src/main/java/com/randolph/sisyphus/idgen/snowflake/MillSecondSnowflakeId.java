package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.IdOptions;

/**
 * Twitter Snowflake
 * @author : randolph
 * date : 2024/10/2 16:58
 */
public class MillSecondSnowflakeId extends AbstractSnowflakeId{
    public static final byte DEFAULT_TIMESTAMP_BIT_LENGTH = 41;

    public static final byte DEFAULT_MACHINE_ID_BIT_LENGTH = 10;

    public static final byte DEFAULT_SEQUENCE_BIT_LENGTH = 12;

    public MillSecondSnowflakeId(long machineId){
        this(IdOptions.DEFAULT_EPOCH, DEFAULT_TIMESTAMP_BIT_LENGTH, DEFAULT_MACHINE_ID_BIT_LENGTH, DEFAULT_SEQUENCE_BIT_LENGTH, machineId);
    }

    public MillSecondSnowflakeId(int machineIdBitLength, long machineId){
        super(IdOptions.DEFAULT_EPOCH, DEFAULT_TIMESTAMP_BIT_LENGTH, (byte) machineIdBitLength, DEFAULT_SEQUENCE_BIT_LENGTH, machineId);
    }

    public MillSecondSnowflakeId(long epoch, byte timestampBitLength, byte machineIdBitLength, byte sequenceBitLength, long machineId) {
        super(epoch, timestampBitLength, machineIdBitLength, sequenceBitLength, machineId);
    }

    @Override
    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
