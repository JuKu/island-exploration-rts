package com.jukusoft.rts.game;

import com.jukusoft.rts.core.Game;
import com.teamunify.i18n.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RTSGame implements Game {

    public RTSGame () {
        //
    }

    @Override
    public void createNewGame(String mapPath) throws IOException {
        //check, if map exists
        if (!new File(mapPath).exists()) {
            throw new FileNotFoundException(I.trf("Couldnt find map: {0}", mapPath));
        }
    }

}
