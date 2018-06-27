package com.jukusoft.rts.core.map;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    protected List<MapMeta> maps = new ArrayList<>();

    protected static final MapManager instance = new MapManager();

    protected MapManager () {
        //
    }

    public List<MapMeta> listMaps () {
        return this.maps;
    }

    public static MapManager getInstance () {
        return instance;
    }

}
