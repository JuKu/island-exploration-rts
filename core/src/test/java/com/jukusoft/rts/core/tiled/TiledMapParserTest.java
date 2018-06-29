package com.jukusoft.rts.core.tiled;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TiledMapParserTest {

    @Test
    public void testConstructor () {
        new TiledMapParser();
    }

    @Test (expected = NullPointerException.class)
    public void testLoadNullFile () throws IOException {
        TiledMapParser parser = new TiledMapParser();
        parser.load(null);
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadNotExistentFile () throws IOException {
        TiledMapParser parser = new TiledMapParser();
        parser.load(new File("not-existent-file.tmx"));
    }

    @Test
    public void testLoad () throws IOException {
        TiledMapParser parser = new TiledMapParser();
        parser.load(new File("../maps/example/junit/test_island.tmx"));

        assertEquals("1.0", parser.getTmxFormatVersion());
        assertEquals(Orientation.ORTHOGONAL, parser.getOrientation());
        assertEquals(20, parser.getWidth());
        assertEquals(20, parser.getHeight());
        assertEquals(32, parser.getTileWidth());
        assertEquals(32, parser.getTileHeight());

        assertEquals(640, parser.getWidthInPixels());
        assertEquals(640, parser.getHeightInPixels());
    }

}
