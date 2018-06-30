package com.jukusoft.rts.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.map.island.IslandParser;
import com.jukusoft.rts.core.speed.GameSpeed;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.core.utils.FileUtils;
import com.jukusoft.rts.core.utils.Utils;
import com.teamunify.i18n.I;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    //list with all islands on map
    protected ObjectArrayList<Island> islands = null;

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
    public void loadAsync() throws Exception {
        LocalLogger.print("load RTSGame");

        //get map path
        String mapPath = this.map.getDir();

        LocalLogger.print("load map directory: " + mapPath);

        //load islands
        Utils.printSection("islands");

        String islandsJsonPath = mapPath + "islands.json";
        LocalLogger.print("islands json path: " + islandsJsonPath);

        if (!new File(islandsJsonPath).exists()) {
            LocalLogger.warn("islands json file doesnt exists: " + islandsJsonPath);
            throw new IllegalStateException("islands json file doesnt exists");
        }

        String content = FileUtils.readFile(islandsJsonPath, StandardCharsets.UTF_8);
        JSONObject islandsJSON = new JSONObject(content);

        //parse islands
        IslandParser islandParser = new IslandParser();
        this.islands = islandParser.parseIslands(islandsJSON, this.map.getDir());

        LocalLogger.print("islands loaded successfully.");

        Utils.printSection("entities");

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
        this.islands = null;
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

    @Override
    public ObjectArrayList<Island> listIslands() {
        return this.islands;
    }

}
