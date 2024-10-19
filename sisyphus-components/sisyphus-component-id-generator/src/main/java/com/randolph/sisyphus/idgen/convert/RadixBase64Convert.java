package com.randolph.sisyphus.idgen.convert;

/**
 * @author : randolph
 * date : 2024/10/9 20:33
 */
public class RadixBase64Convert {

    private static final int MASK_BITS = 6;

    private static final int MAX_CHARS = 11;

    private static final int MASK = 0x1F;

    private static final char[] ENCODING_BASE64_CHARS = {
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z','a','b','c','d',
            'e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x',
            'y','z','+','/'
    };
    private static final int[] fromBase64 = new int[128];



}
