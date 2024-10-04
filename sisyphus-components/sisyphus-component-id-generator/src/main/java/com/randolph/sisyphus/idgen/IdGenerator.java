package com.randolph.sisyphus.idgen;


/**
 * @author : randolph
 * date : 2024/9/29 23:04
 */

public interface IdGenerator {

    long nextId();

    String parseId(long id);
}
