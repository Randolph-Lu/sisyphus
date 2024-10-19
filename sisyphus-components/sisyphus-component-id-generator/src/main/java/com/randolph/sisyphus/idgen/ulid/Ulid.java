package com.randolph.sisyphus.idgen.ulid;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.random.RandomGenerator;

/**
 * @author : randolph
 * date : 2024/10/9 19:06
 */
public class Ulid implements Comparable<Ulid>,Serializable {

    @Serial
    private static final long serialVersionUID = -1;

    protected final RandomGenerator l32X64MixRandom = RandomGenerator.of("L32X64MixRandom");

    private static final char[] ENCODING_BASE32_CHARS = {
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','J','K','M',
            'N','P','Q','R','S','T','V','W','X','Y','Z'
    };

    private static final int[] fromCrockfordBase32 = new int[128];
    static{
        Arrays.fill(fromCrockfordBase32, -1);
        for(int i =0; i < ENCODING_BASE32_CHARS.length; i++){
            fromCrockfordBase32[ENCODING_BASE32_CHARS[i]] = i;
            if (Character.isLetter(ENCODING_BASE32_CHARS[i])) {
                fromCrockfordBase32[Character.toLowerCase(ENCODING_BASE32_CHARS[i])] = i;
            }
        }
        fromCrockfordBase32['I'] = 1;
        fromCrockfordBase32['L'] = 1;
        fromCrockfordBase32['O'] = 0;

        fromCrockfordBase32['i'] = 1;
        fromCrockfordBase32['l'] = 1;
        fromCrockfordBase32['o'] = 0;
    }

    /**
     * The most significant 64 bit of this ULID
     */
    protected long mostSignificantBit;

    /**
     * The least significant 64 bit of this ULID
     */
    protected long leastSignificantBit;

    protected static final long TIMESTAMP_OVERFLOW_MASK = 0xFFFF_0000_0000_0000L;

    protected static final long TIMESTAMP_MSB_MASK = 0xFFFF_FFFF_FFFF_0000L;

    /**
     * The least significant 64 bits increase overflow
     */
    protected static final long LEAST_SIGNIFICANT_BIT_OVERFLOW = 0xFFFF_FFFF_FFFF_FFFFL;

    protected static final int ULID_CHAR_SIZE = 26;

    protected static final int SIGNIFICANT_BIT_MAX_CHARS = 13;

    protected static final int ULID_BYTE_LENGTH = 16;

    protected static final int RANDOMNESS_BYTE_LENGTH = 10;

    protected static final int MASK_BITS = 5;

    protected Ulid(){
        this(0L, 0L);
    }

    protected Ulid(long msb, long lsb) {
        this.mostSignificantBit  = msb;
        this.leastSignificantBit = lsb;
    }

    protected Ulid(Ulid ulid){
        this.mostSignificantBit = ulid.mostSignificantBit;
        this.leastSignificantBit = ulid.leastSignificantBit;
    }

    protected long timestamp(){
        return mostSignificantBit >>> 16;
    }

    protected Ulid increment() {
        long msb = this.mostSignificantBit;
        long lsb = this.leastSignificantBit;
        if (lsb != LEAST_SIGNIFICANT_BIT_OVERFLOW){
            return new Ulid(msb, lsb+1);
        }
        // Overflow! msb increment
        if ((msb & 0xffffL) != 0xffffL) {
            return new Ulid(msb, 0);
        }
        return new Ulid(msb & TIMESTAMP_MSB_MASK, 0);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.mostSignificantBit ^ this.leastSignificantBit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj.getClass() != Ulid.class){
            return false;
        }

        Ulid ulid = (Ulid) obj;
        return this.mostSignificantBit == ulid.mostSignificantBit && this.leastSignificantBit == ulid.leastSignificantBit;
    }

    @Override
    public int compareTo(Ulid val) {
        int val0 = Long.compare(this.mostSignificantBit, val.mostSignificantBit);
        int val1 = Long.compare(this.leastSignificantBit, val.leastSignificantBit);
        return (val0 != 0) ? val0 : val1;
    }

    @Override
    public String toString() {
        return toCanonicalString();
    }

    public String toCanonicalString(){
        char[] buffer = new char[ULID_CHAR_SIZE];
        internalWriteBase32(buffer, this.mostSignificantBit, 0);
        internalWriteBase32(buffer, this.leastSignificantBit, 13);
        return new String(buffer);
    }

    static void internalWriteBase32(char[] buffer, long value, int offset){
        for(int i = 0; i < 13; i++) {
            int index = (int) (value >> (12 - i) * MASK_BITS) & 0x1F;
            buffer[offset + i] = ENCODING_BASE32_CHARS[index];
        }
    }

    static long internalParseCrockford(String input){
        Objects.requireNonNull(input, "The input must not be null!");

        assert input.length() <= SIGNIFICANT_BIT_MAX_CHARS
                : String.format("The input must not exceed 13, otherwise it will cause overflow. but was %d", input.length());

        long result = 0;
        for(int i = 0; i < input.length(); i++) {
            char val = input.charAt(i);
            int tar = (val < fromCrockfordBase32.length) ? fromCrockfordBase32[val] : -1;
            assert tar >= 0 : String.format("Illegal character %s", val);
            result |= (long) tar << ((input.length() - i -1) * MASK_BITS);
        }
        return result;
    }
}
