package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.exception.ClockBackwardException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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
        if ((timestampBitLength + machineIdBitLength + sequenceBitLength) > TOTAL_BITS) {
            throw new IllegalArgumentException("total bit can't be greater than TOTAL_BIT[63]!");
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

    public String parseId(long id) {
        long totalBits = TOTAL_BITS;

        long sequenceNo = (id << (totalBits - sequenceBitLength + 1)) >>> (totalBits - sequenceBitLength + 1);
        long machine = (id << (timestampBitLength + 1 )) >>> (totalBits - machineIdBitLength + 1);
        long diffTimeTick = id >> (machineIdBitLength + sequenceBitLength);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(TimeUnit.MILLISECONDS.toMillis(diffTimeTick + epoch)), ZoneId.systemDefault());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                id, localDateTime.format(timeFormatter), machine, sequenceNo);
    }

    protected long tilNextTimeTick(){
        long currentTime = getCurrentTime();
        while (currentTime <= lastTimeTick){
            currentTime = getCurrentTime();
        }
        return currentTime;
    }

    protected abstract long getCurrentTime();
}
