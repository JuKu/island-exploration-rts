package com.jukusoft.rts.core.map.island;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IslandTest {

    @Test
    public void testConstructor () {
        new Island(1, "test", 0, 0, 200, 200, "island.tmx");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor1 () {
        new Island(-1, "test", 0, 0, 200, 200, "island.tmx");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructor2 () {
        new Island(1, null, 0, 0, 200, 200, "island.tmx");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor3 () {
        new Island(1, "", 0, 0, 200, 200, "island.tmx");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor4 () {
        new Island(1, "test", -1, 0, 200, 200, "island.tmx");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructor5 () {
        new Island(1, "test", 0, -1, 200, 200, "island.tmx");
    }

    @Test
    public void testNullPositionConstructor () {
        new Island(1, "test", 0, 0, 200, 200, "island.tmx");
    }

    @Test
    public void testGetter () {
        Island island = new Island(1, "test", 10, 20, 30, 40, "island.tmx");

        assertEquals(1, island.getId());
        assertEquals("test", island.getTitle());
        assertEquals(10, island.getX(), 0.0001f);
        assertEquals(20, island.getY(), 0.0001f);
        assertEquals(30, island.getWidth());
        assertEquals(40, island.getHeight());
        assertEquals("island.tmx", island.getTmxPath());
    }

}
