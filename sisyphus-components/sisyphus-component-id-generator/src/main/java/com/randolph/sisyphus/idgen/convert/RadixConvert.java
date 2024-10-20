package com.randolph.sisyphus.idgen.convert;

import com.randolph.sisyphus.idgen.IdConvert;

/**
 * @author : randolph
 * date : 2024/10/8 18:10
 */
public class RadixConvert implements IdConvert {

    private final int charSize;

    public RadixConvert(int charSize) {
        this.charSize = charSize;
    }

    @Override
    public String asString(long id) {
        return null;
    }

    @Override
    public Long asLong(String idStr) {
        return null;
    }
}
