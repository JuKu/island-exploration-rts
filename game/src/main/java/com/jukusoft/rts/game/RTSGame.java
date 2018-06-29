package com.jukusoft.rts.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.core.speed.GameSpeed;
import com.jukusoft.rts.core.time.GameTime;
import com.teamunify.i18n.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RTSGame implements Game {

    //sea / ocean world with ships, fishes and so on
    protected World seaWorld = null;

    //entity-component-system
    protected World world = null;

    //game time
    protected GameTime time = GameTime.getInstance();

    //game speed
    protected GameSpeed speed = GameSpeed.getInstance();

    //current map
    protected MapMeta map = null;

    //flag, if it is a new game or a loaded game
    protected boolean newGame = false;

    public RTSGame () {
        //
    }

    @Override
    public void createNewGame(MapMeta map) throws IOException {
        //check, if map exists
        if (!new File(map.getDir()).exists()) {
            throw new FileNotFoundException(I.trf("Couldnt find map: {0}", map.getDir()));
        }

        this.map = map;

        //set flag
        this.newGame = true;
    }

    @Override
    public void loadGame(String saveName) {
        //TODO: add code here

        //set flag
        this.newGame = false;

        //TODO: remove this line later
        this.map = new MapMeta();
    }

    @Override
    public void loadAsync() {
        LocalLogger.print("load RTSGame");

        WorldConfiguration config = new WorldConfiguration();
        WorldConfiguration seaConfig = new WorldConfiguration();

        //TODO: add config here
        config.expectedEntityCount(1000);
        seaConfig.expectedEntityCount(200);

        //TODO: add systems

        LocalLogger.print("create entity-component-system");

        //create entity-component-system
        this.world = new World(config);
        this.seaWorld = new World(seaConfig);
    }

    @Override
    public void update() {
        //first, calculate delta
        float delta = time.getDelta() * speed.getSpeed();

        //update ecs
        this.world.setDelta(delta);
        this.world.process();

        this.seaWorld.setDelta(delta);
        this.seaWorld.process();
    }

    @Override
    public World getSeaWorld() {
        return this.seaWorld;
    }

    @Override
    public void reset() {

    }

    @Override
    public MapMeta getMapMeta() {
        return this.map;
    }

    @Override
    public float[] getCameraStartPosition() {
        if (this.newGame) {
            //use camera start position from map
            return new float[] {
                   map.getCameraStartX(), map.getCameraStartY()
            };
        }

        return new float[]{ 100, 100 };
    }

}
