package com.jukusoft.rts.core.tiled.tileset;

public abstract class Tileset {

    protected final int firstTileID;

    public Tileset (int firstTileID) {
        this.firstTileID = firstTileID;
    }

    public int getFirstTileID() {
        return firstTileID;
    }

}
