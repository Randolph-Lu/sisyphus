package com.randolph.sisyphus.idgen.snowflake;


import org.junit.jupiter.api.Test;

/**
 * @author : randolph
 * date : 2024/10/2 19:45
 */
class AbstractSnowflakeIdTest {

    @Test
    public void testMillSecondSnowflakeIdGenerator() {
        MillSecondSnowflakeId snowflakeId = new MillSecondSnowflakeId(1);
        for(int i = 0; i <= 1000; i++ ){
            long id = snowflakeId.nextId();
            System.out.println(id);// 1727966780838
        }
    }

    @Test
    void testSecondSnowflakeIdGenerator() {
        SecondSnowflakeId snowflakeId = new SecondSnowflakeId(1);
        for (int i = 0; i <= 1000; i++){
            long id = snowflakeId.nextId();
            System.out.println(id);
        }
    }

    @Test
    void testParse() {
        MillSecondSnowflakeId snowflakeId = new MillSecondSnowflakeId(1);
        long id = snowflakeId.nextId();
        System.out.println(id);
    }
}