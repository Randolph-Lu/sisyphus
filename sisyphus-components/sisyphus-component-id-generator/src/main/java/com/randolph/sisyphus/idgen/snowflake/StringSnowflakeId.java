package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.IdConvert;
import com.randolph.sisyphus.idgen.StringIdGeneratorDecorator;

/**
 * @author : randolph
 * date : 2024/10/20 15:36
 */
public class StringSnowflakeId extends StringIdGeneratorDecorator implements SnowflakeId {

    private final SnowflakeId instance;

    public StringSnowflakeId(SnowflakeId idGenerator, IdConvert idConvert) {
        super(idGenerator, idConvert);
        this.instance = idGenerator;
    }

    @Override
    public long getEpoch() {
        return instance.getEpoch();
    }

    @Override
    public byte getTimestampBitLength() {
        return instance.getTimestampBitLength();
    }

    @Override
    public byte getMachineIdBitLength() {
        return instance.getMachineIdBitLength();
    }

    @Override
    public byte getSequenceBitLength() {
        return instance.getSequenceBitLength();
    }

    @Override
    public long getMaxTimestamp() {
        return instance.getMaxTimestamp();
    }

    @Override
    public long getMaxSequence() {
        return instance.getMaxSequence();
    }

    @Override
    public int getMaxMachineId() {
        return instance.getMaxMachineId();
    }
}
