package com.randolph.sisyphus.idgen;


import com.randolph.sisyphus.idgen.convert.RadixBase32Convert;

/**
 * @author : randolph
 * date : 2024/9/29 23:04
 */

public interface IdGenerator extends StringIdGenerator {

    default IdConvert getIdConvert() {
        return new RadixBase32Convert();
    }

    long nextId();

    @Override
    default String generateAsString() {
        return getIdConvert().asString(nextId());
    }
}
