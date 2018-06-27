package com.jukusoft.rts.game;

import com.jukusoft.rts.core.Game;
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
        game.createNewGame("../maps/not-existent-map");
    }

    @Test
    public void testCreateNewGame () throws IOException {
        Game game = new RTSGame();
        game.createNewGame("../maps/example");
    }

}
