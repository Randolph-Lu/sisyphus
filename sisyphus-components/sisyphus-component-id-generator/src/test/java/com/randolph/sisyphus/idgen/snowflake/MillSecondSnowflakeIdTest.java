package com.randolph.sisyphus.idgen.snowflake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : randolph
 * date : 2024/10/4 16:33
 */
class MillSecondSnowflakeIdTest {

    private static final int TEST_MACHINE_ID = 1;
    MillSecondSnowflakeId idGen;
    MillSecondSnowflakeIdAttrParse parse;

    @BeforeEach
    void setUp() {
        idGen = new MillSecondSnowflakeId(TEST_MACHINE_ID);
        parse = new MillSecondSnowflakeIdAttrParse(idGen.epoch, idGen.sequenceBitLength, idGen.machineIdBitLength, idGen.timestampBitLength);
    }

    @Test
    public void generate(){
        long firstId = idGen.nextId();
        long secondId = idGen.nextId();
        Assertions.assertTrue(firstId < secondId);
        SnowflakeIdAttribute idFirstParse = parse.parse(firstId);
        SnowflakeIdAttribute idSecondParse = parse.parse(secondId);
        System.out.println(String.format("first id - time: %s, machine: %d, sequence: %d", idFirstParse.getTimestamp().toString(), idFirstParse.getMachineId(), idFirstParse.getSequence()));
        System.out.println(String.format("second id - time: %s, machine: %d, sequence: %d", idSecondParse.getTimestamp().toString(), idSecondParse.getMachineId(), idSecondParse.getSequence()));
        Assertions.assertNotNull(idFirstParse);
        Assertions.assertEquals(TEST_MACHINE_ID, idFirstParse.getMachineId());
        Assertions.assertNotNull(idFirstParse.toString());

    }
}