package com.randolph.sisyphus.idgen.ulid;

import com.google.common.base.Stopwatch;
import com.randolph.sisyphus.idgen.IdConvert;
import com.randolph.sisyphus.idgen.convert.RadixBase32Convert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : randolph
 * date : 2024/10/16 18:36
 */
class UlidGeneratorTest {

    UlidGenerator ulidGenerator;
    Ulid previousMonotonicUlid;
    IdConvert idConvert;
    Set<Ulid> ulidSet;
    private Stopwatch stopwatch;

    @BeforeEach
    void setUp(){
        ulidGenerator = new UlidGenerator();
        idConvert = new RadixBase32Convert();
        ulidSet = new HashSet<>();
        previousMonotonicUlid = ulidGenerator.nextMonotonicUlid();
        stopwatch = Stopwatch.createStarted();
    }

    @AfterEach
    void tearDown(){
        stopwatch.stop(); // 停止计时器
        long elapsedMilliseconds = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Test duration: " + elapsedMilliseconds + " milliseconds");
    }

    @Test
    void monotonicUlid(){
        Ulid ulid0 = ulidGenerator.nextMonotonicUlid();
        System.out.println(consolePrintFormat(ulid0, 0));
        Ulid ulid1 = ulidGenerator.nextMonotonicUlid();
        System.out.println(consolePrintFormat(ulid1, 1));
        Ulid ulid2 = ulidGenerator.nextMonotonicUlid();
        System.out.println(consolePrintFormat(ulid2, 2));
        Ulid ulid3 = ulidGenerator.nextMonotonicUlid();
        System.out.println(consolePrintFormat(ulid3, 3));

        Assertions.assertTrue(ulid0.compareTo(ulid1) < 0);
        Assertions.assertTrue(ulid1.compareTo(ulid2) < 0);
        Assertions.assertTrue(ulid2.compareTo(ulid3) < 0);

    }

    @Test
    void monotonicUlidForeach(){
        int testCount = 1000;
        for (int i =0; i < testCount; i++) {
            Ulid current = ulidGenerator.nextMonotonicUlid();
            assertTrue(current.compareTo(previousMonotonicUlid) > 0, () -> "The value should be monotonic increasing. Previous: " + previousMonotonicUlid + ", Current: " + current);
            assertFalse(ulidSet.contains(current),
                    () -> "The value should be unique. Value: " + current);
            ulidSet.add(current);
            previousMonotonicUlid = current;
//            System.out.println("index"+ i + " to string:" + ulid);
//            System.out.println(consolePrintFormat(ulid, i));
        }
        assertEquals(testCount, ulidSet.size(), "Repeated ULIDs appear in the process of generating 100 ULIDs");
    }

    @Test
    void strictlyMonotonicUlid(){
        int testCount = 1000;
        for (int i = 0; i < testCount; i++){
            Ulid current = ulidGenerator.nextStrictlyMonotonicUlid();
//            System.out.println("index"+ i + " to string:" + current);
            assertTrue(current.compareTo(previousMonotonicUlid) > 0, () -> "The value should be monotonic increasing. Previous: " + previousMonotonicUlid + ", Current: " + current);
            assertFalse(ulidSet.contains(current),
                    () -> "The value should be unique. Value: " + current);
            ulidSet.add(current);
            previousMonotonicUlid = current;
        }
        assertEquals(testCount, ulidSet.size(), "Repeated ULIDs appear in the process of generating 100 ULIDs");
    }

    @Test
    void overflow(){
        Ulid overflow = new Ulid(113329130049377926L, -1L);
        System.out.println("the least significant bit not overflow: " + overflow + " msb is: " + overflow.mostSignificantBit + " lsb is: " + overflow.leastSignificantBit);

        Ulid increment = overflow.increment();
        System.out.println("the least significant bit overflow: " + increment + " msb is: " + increment.mostSignificantBit + " lsb is: " + increment.leastSignificantBit);
    }

    @Test
    void parseUlid(){
        String ulid = ulidGenerator.generateAsString();
        System.out.println(ulid);
        Ulid parseUlid = ulidGenerator.parseUlid(ulid);
        System.out.println(parseUlid + "msb: " + parseUlid.mostSignificantBit + " lsb: " + parseUlid.leastSignificantBit);


        Long msb = idConvert.asLong(ulid.substring(0, 13));
        Long lsb = idConvert.asLong(ulid.substring(13));
        Ulid convertUlid = new Ulid(msb, lsb);
        System.out.println(convertUlid+ "msb: " + convertUlid.mostSignificantBit + " lsb: " + convertUlid.leastSignificantBit);

        assertEquals(parseUlid, convertUlid);
    }

    private String consolePrintFormat(Ulid ulid, int index){
        return String.format("ulid %d - the most significant is: %d , the least significant is: %d%n", index, ulid.mostSignificantBit, ulid.leastSignificantBit);
    }

}