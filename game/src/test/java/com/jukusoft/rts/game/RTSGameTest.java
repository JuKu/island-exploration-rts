package com.jukusoft.rts.game;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.map.MapMeta;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RTSGameTest {

    @Test
    public void testConstructor () {
        new RTSGame();
    }

    @Test (expected = FileNotFoundException.class)
    public void testCreateNewGameWithNotExistentMap () throws IOException {
        Game game = new RTSGame();

        MapMeta map = new MapMeta();
        map.load("../maps/not-existent-map", "not-existent-map");

        game.createNewGame(map);
    }

    @Test
    public void testCreateNewGame () throws IOException {
        Game game = new RTSGame();

        MapMeta map = new MapMeta();
        map.load("../maps/example", "example");

        game.createNewGame(map);
    }

}
