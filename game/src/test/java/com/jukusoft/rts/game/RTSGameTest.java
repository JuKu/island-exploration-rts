package com.jukusoft.rts.game;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.map.MapMeta;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RTSGameTest {

    @Test
    public void testConstructor () {
        new RTSGame();
    }

    @Test (expected = FileNotFoundException.class)
    public void testCreateNewGameWithNotExistentMap () throws IOException {
        Game game = new RTSGame();

        MapMeta map = Mockito.mock(MapMeta.class);
        Mockito.when(map.getDir()).thenReturn("not-existent-directory");

        game.createNewGame(map);
    }

    @Test
    public void testCreateNewGame () throws Exception {
        Game game = new RTSGame();

        MapMeta map = new MapMeta();
        map.load("../maps/example/", "example");

        game.createNewGame(map);
        game.loadAsync();
        game.update();

        assertNotNull(game.getMapMeta());
        assertNotNull(game.getSeaWorld());
        assertEquals(true, game.getCameraStartPosition()[0] > 0);
        assertEquals(true, game.getCameraStartPosition()[1] > 0);
    }

    @Test
    public void testLoadGame () throws Exception {
        Game game = new RTSGame();
        game.loadGame("test");

        game.getMapMeta().setDir("../maps/example/");

        game.loadAsync();
        game.update();

        assertNotNull(game.getMapMeta());
        assertNotNull(game.getSeaWorld());
        assertEquals(true, game.getCameraStartPosition()[0] > 0);
        assertEquals(true, game.getCameraStartPosition()[1] > 0);
    }

    @Test (expected = IllegalStateException.class)
    public void testLoadGame1 () throws Exception {
        Game game = new RTSGame();
        game.loadGame("test");

        game.loadAsync();
        game.update();

        assertNotNull(game.getMapMeta());
        assertNotNull(game.getSeaWorld());
        assertEquals(true, game.getCameraStartPosition()[0] > 0);
        assertEquals(true, game.getCameraStartPosition()[1] > 0);
    }

    @Test
    public void testResetGame () throws IOException {
        Game game = new RTSGame();
        game.reset();

        MapMeta map = new MapMeta();
        map.load("../maps/example/", "example");

        game.createNewGame(map);

        game.reset();
    }

}
