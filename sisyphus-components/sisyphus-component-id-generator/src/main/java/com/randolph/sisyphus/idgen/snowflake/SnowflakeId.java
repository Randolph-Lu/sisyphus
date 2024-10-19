package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.IdGenerator;

/**
 * @author : randolph
 * date : 2024/10/2 16:56
 */
public interface SnowflakeId extends IdGenerator {
    int TOTAL_BITS = 64;

    int SIGN_BIT = 1;

    long getEpoch();

    byte getTimestampBitLength();

    byte getMachineIdBitLength();

    byte getSequenceBitLength();

    long getMaxTimestamp();
    long getMaxSequence();
    int getMaxMachineId();

}
