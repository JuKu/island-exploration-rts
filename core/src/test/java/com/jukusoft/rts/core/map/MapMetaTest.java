package com.jukusoft.rts.core.map;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MapMetaTest {

    @Test
    public void testConstructor () {
        new MapMeta();
    }

    @Test (expected = NullPointerException.class)
    public void testLoadNullPath () throws IOException {
        new MapMeta().load(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoadEmptyPath () throws IOException {
        new MapMeta().load("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLoadInvalidePath () throws IOException {
        new MapMeta().load("my-path-without-slash");
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentPath () throws IOException {
        new MapMeta().load("not-existent-directory/");
    }

    @Test
    public void testLoad () throws IOException {
        MapMeta map = new MapMeta();
        map.load("../maps/example/");
    }

}
