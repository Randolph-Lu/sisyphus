package com.randolph.sisyphus.idgen.exception;

/**
 * @author : randolph
 * date : 2024/10/2 11:14
 */
public class ClockBackwardException extends BaseException {

    public ClockBackwardException(long lastTimeTick, long currentTimeTick){
        super(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimeTick - currentTimeTick));
    }
}
