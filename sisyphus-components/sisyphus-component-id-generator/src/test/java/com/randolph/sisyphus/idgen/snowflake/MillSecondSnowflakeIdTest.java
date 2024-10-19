package com.randolph.sisyphus.idgen.snowflake;

import com.randolph.sisyphus.idgen.convert.RadixBase32Convert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author : randolph
 * date : 2024/10/4 16:33
 */
class MillSecondSnowflakeIdTest {

    private static final int TEST_MACHINE_ID = 1;
    MillSecondSnowflakeId idGen;
    MillSecondSnowflakeIdAttrParser parse;

    @BeforeEach
    void setUp() {
        idGen = new MillSecondSnowflakeId(TEST_MACHINE_ID);
        parse = new MillSecondSnowflakeIdAttrParser(idGen.epoch, idGen.sequenceBitLength, idGen.machineIdBitLength, idGen.timestampBitLength);
    }

    @Test
    void generate(){
        long firstId = idGen.nextId();
        long secondId = idGen.nextId();
        Assertions.assertTrue(firstId < secondId);
        SnowflakeIdAttribute idFirstParse = parse.parse(firstId);
        SnowflakeIdAttribute idSecondParse = parse.parse(secondId);
        System.out.println(String.format("first id - %s", idFirstParse.toString()));
        System.out.println(String.format("second id - %s", idSecondParse.toString()));
        Assertions.assertNotNull(idFirstParse);
        Assertions.assertEquals(TEST_MACHINE_ID, idFirstParse.getMachineId());
        Assertions.assertNotNull(idFirstParse.toString());

    }

    @Test
    void convertByCrockfordBase32(){
        Assertions.assertTrue(idGen.getIdConvert() instanceof RadixBase32Convert);
        for (int i = 0; i < 100; i++) {
            long snowflakeId = idGen.nextId();
            String asString = idGen.getIdConvert().asString(snowflakeId);
            System.out.println(snowflakeId);
            System.out.println(String.format("convert by crockford's Base32 : %s", asString));
        }
    }

}