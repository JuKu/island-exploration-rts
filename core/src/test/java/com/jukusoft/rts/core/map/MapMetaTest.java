package com.jukusoft.rts.core.map;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MapMetaTest {

    @Test
    public void testConstructor () {
        new MapMeta();
    }

    @Test (expected = NullPointerException.class)
    public void testLoadNullPath () throws IOException {
        new MapMeta().load(null, "test");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoadEmptyPath () throws IOException {
        new MapMeta().load("", "test");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoadInvalidePath () throws IOException {
        new MapMeta().load("my-path-without-slash", "test");
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentPath () throws IOException {
        new MapMeta().load("not-existent-directory/", "test");
    }

    @Test
    public void testLoad () throws IOException {
        MapMeta map = new MapMeta();
        map.load("../maps/example/", "test");

        assertEquals("test", map.getName());
    }

}
