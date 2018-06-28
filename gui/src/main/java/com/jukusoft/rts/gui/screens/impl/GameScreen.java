package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.speed.GameSpeed;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.renderer.water.WaterRenderer;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
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

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        // create sprite batcher
        this.batch = new SpriteBatch();
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
    }

    protected void renderGame (Game game, CameraHelper camera, SpriteBatch batch) {
        //render water
        this.waterRenderer.draw(game, this.time, camera, batch);
    }

    /**
    * will be executed from LoadGameScreen in another thread
    */
    public void loadAsync () {
        LocalLogger.print(I.tr("create camera"));

        //create new camera
        this.camera = new CameraHelper(1280, 720);

        //load resources asynchronous

        //set flag
        this.loaded.set(true);
    }

}
