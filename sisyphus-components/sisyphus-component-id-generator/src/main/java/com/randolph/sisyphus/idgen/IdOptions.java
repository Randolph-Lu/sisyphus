package com.randolph.sisyphus.idgen;

import java.time.LocalDateTime;

/**
 * @author : randolph
 * date : 2024/10/2 17:32
 */
public final class IdOptions {
    public static final LocalDateTime EPOCH_DATE;

    public static final long EPOCH = 1727726400000L;

    public static final Long EPOCH_SECOND = 1727726400L;

    static {
        EPOCH_DATE = LocalDateTime.of(2024, 10, 1, 4, 0);
    }
    
    private IdOptions(){
        
    }
}
