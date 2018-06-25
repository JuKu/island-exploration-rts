package com.jukusoft.rts.core.cache;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CacheTest {

    @Test (expected = NullPointerException.class)
    public void testNullConstructor () throws IOException {
        new Cache(null);
    }

    @Test (expected = FileNotFoundException.class)
    public void testNotExistentFileConstructor () throws IOException {
        new Cache(new File("not-existent-file.cfg"));
    }

    @Test
    public void testConstructor () throws IOException {
        Cache cache = new Cache(new File("../config/cache.cfg"));
        assertEquals("./cache/", cache.getPath());

        //delete cache directory again
        new File("./cache/").delete();
    }

    @Test
    public void testConstructor1 () throws IOException {
        //delete cache directory first, if exists
        if (new File("../cache/").exists()) {
            new File("../cache/").delete();
        }

        Cache cache = new Cache(new File("../config/junit-cache.cfg"));

        assertEquals("../cache/", cache.getPath());

        //check, if cache directory exists
        assertEquals(true, new File("../cache/").exists());
    }

    @Test (expected = IllegalStateException.class)
    public void testGetInstance () {
        Cache.instance = null;

        Cache.getInstance();
    }

    @Test
    public void testGetInstance1 () throws IOException {
        Cache.instance = null;

        Cache.init(new File("../config/cache.cfg"));

        Cache instance = Cache.getInstance();
        assertNotNull(instance);

        //check, that instances are equals
        Cache instance1 = Cache.getInstance();
        assertNotNull(instance1);
        assertEquals(instance, instance1);
    }

}
