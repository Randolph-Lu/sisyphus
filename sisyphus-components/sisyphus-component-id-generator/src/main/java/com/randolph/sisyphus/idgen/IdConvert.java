package com.randolph.sisyphus.idgen;

/**
 * @author : randolph
 * date : 2024/10/4 17:28
 */
public interface IdConvert {

    String asString(long id);

    Long asLong(String idStr);
}
