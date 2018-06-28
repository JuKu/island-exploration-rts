package com.jukusoft.rts.gui.screens.impl;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.gui.camera.CameraHelper;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.teamunify.i18n.I;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameScreen implements IScreen {

    //camera
    protected CameraHelper camera = null;

    //game time (for simulation)
    protected GameTime time = GameTime.getInstance();

    protected AtomicBoolean loaded = new AtomicBoolean(false);

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void onStop(Game game) {
        //
    }

    @Override
    public void onResume(Game game) {
        if (!loaded.get()) {
            throw new IllegalStateException("game screen wasnt loaded yet, call loadAsync() first.");
        }
    }

    @Override
    public void onPause(Game game) {
        this.camera = null;
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
    }

    @Override
    public void draw(Game game) {
        //
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
