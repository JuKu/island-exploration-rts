package com.jukusoft.rts.core.map;

public class MapManager {

    protected static final MapManager instance = new MapManager();

    protected MapManager () {
        //
    }

    public static MapManager getInstance () {
        return instance;
    }

}
