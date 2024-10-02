package com.randolph.sisyphus.idgen.snowflake;


/**
 * @author : randolph
 * date : 2024/10/2 19:45
 */
class AbstractSnowflakeIdTest {

    public static void main(String[] args) {
        MillSecondSnowflakeId snowflakeId = new MillSecondSnowflakeId(1);
        for(int i = 0; i <= 1000; i++ ){
            long id = snowflakeId.nextId();
            System.out.println(id);
        }
    }

}