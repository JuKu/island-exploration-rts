package com.jukusoft.rts.core.tiled.tileset;

import java.io.File;

public class TsxTileset extends Tileset {

    public TsxTileset(int firstTileID, String source, int tileCount) {
        super(firstTileID, firstTileID + tileCount - 1);

        //check, if file exists
        if (!new File(source).exists()) {
            throw new IllegalArgumentException("source file doesnt exists.");
        }
    }

}
