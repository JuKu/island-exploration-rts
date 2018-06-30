package com.jukusoft.rts.core.map;

import com.jukusoft.rts.core.tiled.TiledLayer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TiledLayerTest {

    @Test
    public void testConstructor () {
        new TiledLayer("test", 10, 20, 1, true, 30, 40);
    }

    @Test
    public void testGetter () {
        TiledLayer layer = new TiledLayer("test", 10, 20, 1, true, 30, 40);

        assertEquals("test", layer.getName());
        assertEquals(10, layer.getWidth());
        assertEquals(20, layer.getHeight());
        assertEquals(1, layer.getOpacity(), 0.0001f);
        assertEquals(true, layer.isVisible());
        assertEquals(30, layer.getOffsetx(), 0.0001f);
        assertEquals(40, layer.getOffsety(), 0.0001f);
    }

    @Test (expected = NullPointerException.class)
    public void testSetNullTileIDs () {
        TiledLayer layer = new TiledLayer("test", 10, 20, 1, true, 30, 40);

        layer.setTileIDs(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalideTileIDs () {
        TiledLayer layer = new TiledLayer("test", 10, 20, 1, true, 30, 40);

        layer.setTileIDs(new int[10]);
    }

    @Test (expected = IllegalStateException.class)
    public void testGetNullTileIDs () {
        TiledLayer layer = new TiledLayer("test", 10, 20, 1, true, 30, 40);

        layer.getTileIDs();
    }

    @Test
    public void testGetTileIDs () {
        TiledLayer layer = new TiledLayer("test", 10, 20, 1, true, 30, 40);

        layer.setTileIDs(new int[10 * 20]);
        assertNotNull(layer.getTileIDs());
        assertEquals(200, layer.getTileIDs().length);
    }

}
