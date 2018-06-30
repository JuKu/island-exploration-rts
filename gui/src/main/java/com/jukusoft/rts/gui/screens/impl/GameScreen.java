package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.carrotsearch.hppc.ObjectArrayList;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.map.MapMeta;
import com.jukusoft.rts.core.speed.GameSpeed;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.core.utils.Platform;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.island.IslandRenderer;
import com.jukusoft.rts.gui.renderer.water.WaterRenderer;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
import com.teamunify.i18n.I;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameScreen implements IScreen {

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

        //render islands
        this.islandRendererList.iterator().forEachRemaining(renderer -> {
            renderer.value.draw(game, this.time, camera, batch);
        });

        //TODO: render entities
    }

    /**
    * will be executed from LoadGameScreen in another thread
    */
    public void loadAsync () {
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
        if (!this.loaded.get()) {
            throw new IllegalStateException("call loadAsync() before calling loadSync()!");
        }

        //init island renderer
    }

}
