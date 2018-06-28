package com.jukusoft.rts.core;

import com.jukusoft.rts.core.map.MapMeta;

import java.io.IOException;

public interface Game {

    public void createNewGame (MapMeta map) throws IOException;

}
