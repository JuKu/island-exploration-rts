package com.jukusoft.rts.gui.utils;

import com.badlogic.gdx.utils.IntMap;
import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;
import com.jukusoft.rts.core.utils.RandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntMapPerformanceBenchmark {

    protected static final int entryCount = 10000000;

    @Test
    public void testLibGDXIntMapBenchmark () {
        System.out.println("fill map with " + entryCount + " entries.");

        //first, test IntMap from libGDX
        IntMap<Object> map = new IntMap<>();

        long startTime = System.currentTimeMillis();

        //fill with 1.000.000 entries
        for (int i = 0; i < entryCount; i++) {
            map.put(i, new Object());
        }

        long endTime = System.currentTimeMillis();
        long diffTime = endTime - startTime;

        System.out.println("libGDX insert required time: " + diffTime + "ms");

        startTime = System.currentTimeMillis();

        //create list with indexes, map should access to
        int[] indizes = prepareAccessList(entryCount);

        endTime = System.currentTimeMillis();
        diffTime = endTime - startTime;
        System.out.println("time to generate random indizes: " + diffTime + "ms");

        startTime = System.currentTimeMillis();

        //access entries randomly
        for (int i = 0; i < indizes.length; i++) {
            Object obj = map.get(indizes[i]);
        }

        endTime = System.currentTimeMillis();
        diffTime = endTime - startTime;
        System.out.println("time to get random entries: " + diffTime + "ms");
    }

    @Test
    public void testHppcIntObjectMapBenchmark () {
        System.out.println("fill map with " + entryCount + " entries.");

        //first, test IntMap from libGDX
        IntObjectMap<Object> map = new IntObjectHashMap<>();

        long startTime = System.currentTimeMillis();

        //fill with 1.000.000 entries
        for (int i = 0; i < entryCount; i++) {
            map.put(i, new Object());
        }

        long endTime = System.currentTimeMillis();
        long diffTime = endTime - startTime;

        System.out.println("hppc IntObjectMap insert required time: " + diffTime + "ms");

        startTime = System.currentTimeMillis();

        //create list with indexes, map should access to
        int[] indizes = prepareAccessList(entryCount);

        endTime = System.currentTimeMillis();
        diffTime = endTime - startTime;
        System.out.println("time to generate random indizes: " + diffTime + "ms");

        startTime = System.currentTimeMillis();

        //access entries randomly
        for (int i = 0; i < indizes.length; i++) {
            Object obj = map.get(indizes[i]);
        }

        endTime = System.currentTimeMillis();
        diffTime = endTime - startTime;
        System.out.println("hppc IntObjectMap: time to get random entries: " + diffTime + "ms");
    }

    protected static int[] prepareAccessList (int length) {
        int[] array = new int[length];

        for (int i = 0; i < array.length; i++) {
            array[i] = RandomUtils.getRandomNumber(0, array.length);
        }

        return array;
    }

}
