package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.core.map.island.Island;
import com.jukusoft.rts.core.speed.GameSpeed;
import com.jukusoft.rts.core.tiled.TiledMapParser;
import com.jukusoft.rts.core.tiled.tileset.TextureTileset;
import com.jukusoft.rts.core.tiled.tileset.Tileset;
import com.jukusoft.rts.core.tiled.tileset.TsxTileset;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.core.utils.Platform;
import com.jukusoft.rts.core.utils.Utils;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.island.IslandRenderer;
import com.jukusoft.rts.gui.renderer.water.WaterRenderer;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
import com.teamunify.i18n.I;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameScreen implements IScreen {

    //asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //camera
    protected CameraHelper camera = null;

    //game time (for simulation)
    protected GameTime time = GameTime.getInstance();

    //game speed
    protected GameSpeed speed = GameSpeed.getInstance();

    //water renderer
    protected WaterRenderer waterRenderer = null;

    //sprite batch
    protected SpriteBatch batch = null;

    //flag, if screen was loaded
    protected AtomicBoolean loaded = new AtomicBoolean(false);

    //list with island renderer which renders islands on map
    protected ObjectArrayList<IslandRenderer> islandRendererList = new ObjectArrayList<>();

    protected Game game = null;
    protected ScreenManager<IScreen> screenManager = null;

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        // create sprite batcher
        this.batch = new SpriteBatch();

        this.game = game;
        this.screenManager = screenManager;
    }

    @Override
    public void onStop(Game game) {
        this.batch.dispose();
        this.batch = null;
    }

    @Override
    public void onResume(Game game) {
        if (!loaded.get()) {
            throw new IllegalStateException("game screen wasnt loaded yet, call loadAsync() first.");
        }

        //load water renderer
        this.waterRenderer = new WaterRenderer();
        this.waterRenderer.load("data/misc/water/water.atlas", "water", 1000f / 8);
    }

    @Override
    public void onPause(Game game) {
        this.camera = null;

        //dispose water renderer
        this.waterRenderer.dispose();
        this.waterRenderer = null;
    }

    @Override
    public void onResize(int width, int height) {
        //resize camera
        this.camera.resize(width, height);
    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return false;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {
        //update main camera
        this.camera.update(time);

        //update game
        this.updateGame(game, time.getDelta() * speed.getSpeed(), screenManager);
    }

    @Override
    public void draw(Game game) {
        //beginn rendering process
        this.batch.begin();

        //set camera projection matrix
        this.batch.setProjectionMatrix(this.camera.getCombined());

        //render game
        this.renderGame(game, this.camera, this.batch);

        //flush rendering
        this.batch.end();
    }

    protected void updateGame (Game game, float delta, ScreenManager<IScreen> screenManager) {
        //update water
        this.waterRenderer.update(game, this.time);

        //update islands
        this.islandRendererList.iterator().forEachRemaining(renderer -> {
            renderer.value.update(game, this.time);
        });

        //update game entities
        game.update();
    }

    protected void renderGame (Game game, CameraHelper camera, SpriteBatch batch) {
        //render water
        this.waterRenderer.draw(game, this.time, camera, batch);

        //because of TiledMapRenderer, we have end() and begin() batch (limitations of libGDX)
        batch.end();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //render islands
        this.islandRendererList.iterator().forEachRemaining(renderer -> {
            renderer.value.draw(game, this.time, camera, batch);
        });

        batch.begin();

        //TODO: render entities
    }

    /**
    * will be executed from LoadGameScreen in another thread
    */
    public void loadAsync () throws IOException {
        Utils.printSection("GameScreen::loadAsync()");

        LocalLogger.print(I.tr("create camera"));

        //create new camera
        this.camera = new CameraHelper(1280, 720);

        //get current map
        MapMeta map = game.getMapMeta();

        //set camera bounds
        this.camera.setBounds(0, map.getWidth(), 0, map.getHeight());

        float[] cameraPos = game.getCameraStartPosition();

        //set camera start position
        this.camera.forcePos(cameraPos[0], cameraPos[1]);

        //load map and so on
        try {
            game.loadAsync();
        } catch (Exception e) {
            LocalLogger.printStacktrace(e);

            LocalLogger.warn("exception while loading map, go back to main menu.");

            //loading failed, go back to main menu
            Platform.runOnUIThread(() -> screenManager.leaveAllAndEnter(Screens.MAIN_MENU));

            return;
        }

        //init island renderer
        ObjectArrayList<Island> islands = game.listIslands();

        //load tiled map resources
        for (int i = 0; i < game.listIslands().size(); i++) {
            Island island = game.listIslands().get(i);

            //get tmx path
            String tmxPath = island.getTmxPath();

            LocalLogger.print("");

            TiledMapParser parser = new TiledMapParser();
            parser.load(new File(tmxPath));

            //add new renderer
            IslandRenderer renderer = new IslandRenderer(island, parser);

            //add renderer to list
            this.islandRendererList.add(renderer);

            LocalLogger.print("island renderer initialized successfully.");

            //get tilesets
            List<Tileset> tilesets = parser.listTilesets();

            //load textures for tilesets
            for (Tileset tileset : tilesets) {
                if (tileset instanceof TextureTileset) {
                    TextureTileset tileset1 = (TextureTileset) tileset;
                    ObjectArrayList<TextureTileset.TilesetImage> requiredTextures = tileset1.listTextures();

                    //load images
                    for (int k = 0; k < requiredTextures.size(); k++) {
                        TextureTileset.TilesetImage texture = requiredTextures.get(k);

                        //get texture path
                        String texturePath = texture.source;

                        //check, if file exists
                        if (!new File(texturePath).exists()) {
                            throw new FileNotFoundException("required tileset texture doesnt exists: " + texturePath);
                        }

                        //load texture
                        assetManager.load(texturePath, Texture.class);
                    }
                } else if (tileset instanceof TsxTileset) {
                    TsxTileset tileset1 = (TsxTileset) tileset;
                } else {
                    throw new UnsupportedOperationException("tileset type " + tileset.getClass().getSimpleName() + " isnt supported yet.");
                }
            }

            LocalLogger.print("load TiledMap: " + tmxPath);

            //load tiled map
            assetManager.load(tmxPath, TiledMap.class);
        }

        //load resources asynchronous

        //set flag
        this.loaded.set(true);
    }

    /**
    * load synchronous, after loadAsync(), in main thread
     *
     * @see GameScreen
    */
    public void loadSync () {
        Utils.printSection("GameScreen::loadSync()");

        if (!this.loaded.get()) {
            throw new IllegalStateException("call loadAsync() before calling loadSync()!");
        }

        //finish load gpu resources
        islandRendererList.iterator().forEachRemaining(renderer -> renderer.value.loadSync());
    }

}
