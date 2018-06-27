package com.jukusoft.rts.core.map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapManagerTest {

    @Test
    public void testConstructor () {
        new MapManager();
    }

    @Test
    public void testGetInstance () {
        MapManager manager = MapManager.getInstance();
        MapManager manager1 = MapManager.getInstance();

        //check, that instances arent null
        assertNotNull(manager);
        assertNotNull(manager1);

        //check, if instances are the same
        assertEquals(manager, manager1);
    }

}
