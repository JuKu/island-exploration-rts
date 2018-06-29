package com.jukusoft.rts.core.tiled;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TiledMapParser {

    public TiledMapParser () {
        //
    }

    /**
    * load .tmx map
    */
    public void load (File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("tiled map doesnt exists.");
        }
    }

}
