package com.jukusoft.rts.core.map;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapManagerTest {

    @Test
    public void testConstructor () throws IOException {
        new MapManager("../maps/");
    }

    @Test (expected = FileNotFoundException.class)
    public void testConstructorNotExistentDir () throws IOException {
        new MapManager("../not-existent-directory/");
    }

    @Test
    public void testGetInstance () throws IOException {
        MapManager.init("../maps/");

        MapManager manager = MapManager.getInstance();
        MapManager manager1 = MapManager.getInstance();

        //check, that instances arent null
        assertNotNull(manager);
        assertNotNull(manager1);

        //check, if instances are the same
        assertEquals(manager, manager1);

        assertEquals(false, manager.listFreeMaps().isEmpty());
        assertEquals(true, manager.listFreeMaps().size() > 0);
    }

}
