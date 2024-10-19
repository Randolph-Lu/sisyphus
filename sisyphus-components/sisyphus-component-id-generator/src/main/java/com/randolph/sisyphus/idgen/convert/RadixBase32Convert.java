package com.randolph.sisyphus.idgen.convert;

import com.google.common.base.Preconditions;
import com.randolph.sisyphus.idgen.IdConvert;

import java.util.Arrays;

/**
 * @author : randolph
 * date : 2024/10/9 20:33
 */
public class RadixBase32Convert implements IdConvert {

    private static final int MASK_BITS = 5;

    private static final int MAX_CHARS = 13;

    private static final int MASK = 0x1F;

    /**
     * crock-ford's Base32
     * @see  <a href="https://www.crockford.com/base32.html">Base 32</a>
     */
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

    @Override
    public String asString(long id) {
        Preconditions.checkArgument(id > -1, "Id must be greater than -1!");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < MAX_CHARS; i++){
            int index = (int) (id >>> (MAX_CHARS - i - 1)*MASK_BITS) & MASK;
            sb.append(ENCODING_BASE32_CHARS[index]);
        }
        return sb.toString();
    }

    /**
     * @param idStr
     * @return
     */
    @Override
    public Long asLong(String idStr) {
        Preconditions.checkNotNull(idStr,"Input must not be null!");
        Preconditions.checkArgument(idStr.length() <= MAX_CHARS, String.format("Input length must not exceed 13, but was %d !",idStr.length()));
        long result = 0;
        for(int i = 0; i < idStr.length(); i++){
            char c = idStr.charAt(i);
            int val = -1;
            if (c < fromCrockfordBase32.length){
                val = fromCrockfordBase32[c];
            }
            if (val < 0) {
                throw new IllegalArgumentException(String.format("Illegal character %s", c));
            }
            result |= (long) (val) << ((idStr.length() - 1 -i) * MASK_BITS);
        }
        return result;
    }
}
