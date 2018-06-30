package com.jukusoft.rts.core.map.tileset;

import com.jukusoft.rts.core.tiled.tileset.Tileset;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TilesetTest {

    @Test
    public void testConstructor () {
        Tileset tileset = new Tileset(10) {};
        assertEquals(10, tileset.getFirstTileID());
    }

}
