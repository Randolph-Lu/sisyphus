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
            System.out.println(snowflakeId.parseId(id));
        }
    }

    @Test
    void testSecondSnowflakeIdGenerator() {
        SecondSnowflakeId snowflakeId = new SecondSnowflakeId(1);
        for (int i = 0; i <= 1000; i++){
            long id = snowflakeId.nextId();
            System.out.println(id);
            System.out.println(snowflakeId.parseId(id));
        }
    }

    @Test
    void testParse() {
        MillSecondSnowflakeId snowflakeId = new MillSecondSnowflakeId(1);
        long id = snowflakeId.nextId();
        System.out.println(id);
        String parseId = snowflakeId.parseId(id);
        System.out.println(parseId);
    }
}