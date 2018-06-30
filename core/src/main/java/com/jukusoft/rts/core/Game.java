package com.jukusoft.rts.core;

import com.artemis.World;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.core.map.island.Island;

import java.io.IOException;

public interface Game {

    /**
    * create new game based on map
     *
     * @param map map
    */
    public void createNewGame (MapMeta map) throws IOException;

    /**
    * load game
     *
     * @param saveName save name in saves/ directory
    */
    public void loadGame (String saveName);

    /**
    * load game resources, like maps, entities and so on asynchronous on extra thread
    */
    public void loadAsync () throws Exception;

    /**
    * update game logik
    */
    public void update ();

    /**
    * get sea ECS with all ships and so on
    */
    public World getSeaWorld ();

    public void reset ();

    /**
    * get meta information about current map
     *
     * @return map meta data
    */
    public MapMeta getMapMeta ();

    /**
    * get start position of camera
    */
    public float[] getCameraStartPosition ();

    /**
    * get list with all islands on map
     *
     * @return list with islands on map
    */
    public ObjectArrayList<Island> listIslands ();

}
