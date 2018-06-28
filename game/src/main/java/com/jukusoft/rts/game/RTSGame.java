package com.jukusoft.rts.game;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.map.MapMeta;
import com.teamunify.i18n.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RTSGame implements Game {

    public RTSGame () {
        //
    }

    @Override
    public void createNewGame(MapMeta map) throws IOException {
        //check, if map exists
        if (!new File(map.getDir()).exists()) {
            throw new FileNotFoundException(I.trf("Couldnt find map: {0}", map.getDir()));
        }
    }

}
