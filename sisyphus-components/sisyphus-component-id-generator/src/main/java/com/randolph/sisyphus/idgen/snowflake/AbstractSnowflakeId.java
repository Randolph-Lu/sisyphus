package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.exception.ClockBackwardException;

/**
 * @author : randolph
 * date : 2024/10/2 16:56
 */
public abstract class AbstractSnowflakeId implements SnowflakeId {

    protected final long epoch;
    protected final byte timestampBitLength;
    protected final byte machineIdBitLength;
    protected final byte sequenceBitLength;

    protected final long maxTimestamp;
    protected final long maxSequence;
    protected final int maxMachineId;

    protected final long timeStampLeft;
    protected final long machineIdLeft;

    protected long machineId;
    protected long sequence = 0L;
    protected long lastTimeTick = -1L;

    protected AbstractSnowflakeId(long epoch, byte timestampBitLength, byte machineIdBitLength, byte sequenceBitLength, long machineId){
        if ((SIGN_BIT + timestampBitLength + machineIdBitLength + sequenceBitLength) > TOTAL_BITS) {
            throw new IllegalArgumentException("total bit can't be greater than TOTAL_BIT[64]!");
        }
        this.epoch = epoch;
        this.timestampBitLength = timestampBitLength;
        this.machineIdBitLength = machineIdBitLength;
        this.sequenceBitLength = sequenceBitLength;
        this.maxTimestamp = ~(-1L << timestampBitLength);
        this.maxMachineId = ~(-1 << machineIdBitLength);
        this.maxSequence = ~(-1L << sequenceBitLength);
        this.machineIdLeft = sequenceBitLength;
        this.timeStampLeft = this.machineIdLeft + machineIdBitLength;
        this.machineId = machineId;
    }

    @Override
    public synchronized long nextId() {
        long currentTime = getCurrentTime();

        if (lastTimeTick > currentTime) {
            throw new ClockBackwardException(lastTimeTick, currentTime);
        }

        if (lastTimeTick == currentTime) {
            sequence = (sequence+1)&maxSequence;
            if (sequence == 0){
                currentTime = tilNextTimeTick();
            }
        }else {
            sequence = 0L;
        }


        lastTimeTick = currentTime;
        return (((currentTime-epoch) << timeStampLeft)
                + (machineId << machineIdLeft)
                + sequence);
    }

    protected long tilNextTimeTick(){
        long currentTime = getCurrentTime();
        while (currentTime <= lastTimeTick){
            currentTime = getCurrentTime();
        }
        return currentTime;
    }

    protected abstract long getCurrentTime();

    @Override
    public long getEpoch() {
        return epoch;
    }

    @Override
    public byte getTimestampBitLength() {
        return timestampBitLength;
    }

    @Override
    public byte getMachineIdBitLength() {
        return machineIdBitLength;
    }

    @Override
    public byte getSequenceBitLength() {
        return sequenceBitLength;
    }

    @Override
    public long getMaxTimestamp() {
        return maxTimestamp;
    }

    @Override
    public long getMaxSequence() {
        return maxSequence;
    }

    @Override
    public int getMaxMachineId() {
        return maxMachineId;
    }

    public long getLastTimeTick() {
        return lastTimeTick;
    }
}
