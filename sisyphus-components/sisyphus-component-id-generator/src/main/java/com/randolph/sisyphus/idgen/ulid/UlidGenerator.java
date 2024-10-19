package com.randolph.sisyphus.idgen.ulid;

import com.google.common.base.Preconditions;
import com.randolph.sisyphus.idgen.StringIdGenerator;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

/**
 * @author : randolph
 * date : 2024/10/9 20:31
 */
public class UlidGenerator extends Ulid implements StringIdGenerator {

    private Ulid previousUlid;

    private static final int SHIFT_MASK = 0xFF;

    @Override
    public String generateAsString() {
        return generateAsString(getCurrentTimestamp());
    }

    public synchronized Ulid nextMonotonicUlid(){
        long timestamp = getCurrentTimestamp();
        return Objects.nonNull(this.previousUlid) ?
                nextMonotonicUlid(this.previousUlid, timestamp) :
                nextMonotonicUlid(timestamp, ThreadLocalRandom.current());
    }

    /**
     *
     * @return
     */
    public synchronized Ulid nextStrictlyMonotonicUlid(){
        // if the previous ULID is null, generate next monotonic ULID
        if (Objects.isNull(this.previousUlid)){
            nextMonotonicUlid(getCurrentTimestamp(), ThreadLocalRandom.current());
        }
        return nextStrictlyMonotonicUlid(this.previousUlid, System.currentTimeMillis());
    }

    private Ulid nextStrictlyMonotonicUlid(Ulid previousUlid, long currentTimestamp){
        Preconditions.checkNotNull(previousUlid, "Previous ULID must not be null!");
        long previousTime = previousUlid.timestamp();
        if (previousTime == currentTimestamp) {
            this.previousUlid = previousUlid.increment();
            return new Ulid(this.previousUlid);
        }
        Ulid next = previousUlid.increment();
        long msb = (currentTimestamp << 16) | (next.mostSignificantBit & 0xFFFF);
        long lsb = next.leastSignificantBit;
        this.previousUlid = new Ulid(msb, lsb);
        return new Ulid(this.previousUlid);
    }

    /**
     * Base on previous ULID
     *
     * @param previous
     * @param timestamp
     * @return
     */
    public Ulid nextMonotonicUlid(Ulid previous, long timestamp){
        long previousTime = previous.timestamp();
        if (previousTime == timestamp) {
            this.previousUlid = previous.increment();
            return new Ulid(this.previousUlid);
        }
        return nextMonotonicUlid(timestamp, ThreadLocalRandom.current());
    }

    /**
     * Generate new monotonic ULID
     */
    public Ulid nextMonotonicUlid(long timestamp, RandomGenerator randomness){
        long msb = timestamp << 16 | (randomness.nextLong() & 0xFFFFL);
        long lsb = randomness.nextLong();
        this.previousUlid = new Ulid(msb, lsb);
        return new Ulid(this.previousUlid);
    }

    public String generateAsString(long timestamp){
        checkTime(timestamp);
        return nextUlidString(timestamp, ThreadLocalRandom.current());
    }

    private String nextUlidString(long timestamp, RandomGenerator randomGenerator){
        byte[] randomness = new byte[RANDOMNESS_BYTE_LENGTH];
        randomGenerator.nextBytes(randomness);
        return nextUlidString(timestamp, randomness);
    }

    private String nextUlidString(long timestamp, byte[] randomness){
        long msb = mostSignificantBit;
        long lsb = leastSignificantBit;

        byte[] data = new byte[ULID_BYTE_LENGTH];
        byte[] buffer = ByteBuffer.allocate(8).putLong(0, timestamp << 16).array();
        System.arraycopy(buffer, 0, data, 0, 6);
        System.arraycopy(randomness, 0, data, 6, 10);
        for(int i = 0; i < 8; i++){
            msb = (msb << 8) | (data[i] & SHIFT_MASK);
        }
        for(int i = 8; i < 16; i++){
            lsb = (lsb << 8) | (data[i] & SHIFT_MASK);
        }

        return new Ulid(msb, lsb).toString();
    }

    public Ulid parseUlid(String ulidString){
        Preconditions.checkNotNull(ulidString, "ulidString must not be null!");
        if (ulidString.length() != ULID_CHAR_SIZE){
            throw new IllegalArgumentException("ulidString must be exactly 26 chars long.");
        }
        long msb = internalParseCrockford(ulidString.substring(0,13));
        long lsb = internalParseCrockford(ulidString.substring(13));

        long time = msb >> 16;
        checkTime(time);
        return new Ulid(msb, lsb);
    }

    /**
     * Gets the current system time
     * @return {@link Long} current timestamp
     */
    private static long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    private static byte[] defaultRandomness(int byteLength){
        byte[] bytes = new byte[byteLength];
        ThreadLocalRandom.current().nextBytes(bytes);
        return bytes;
    }

    static void checkTime(long timestamp){
        if ((timestamp & TIMESTAMP_OVERFLOW_MASK) != 0){
            throw new IllegalArgumentException("Invalid timestamp argument, timestamp must not exceed 10889-08-02T13:31:50.656");
        }
    }
}
