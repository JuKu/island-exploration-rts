package com.jukusoft.rts.gui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.time.GameTime;
import com.jukusoft.rts.core.utils.Platform;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.fps.FPSManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
import com.jukusoft.rts.gui.screens.impl.DefaultScreenManager;
import com.jukusoft.rts.gui.screens.impl.MainMenuScreen;

public class GameGUI implements ApplicationListener {

    protected final Game game;
    protected final ScreenManager<IScreen> screenManager;
    protected final GameTime time = GameTime.getInstance();
    protected final FPSManager fps = FPSManager.getInstance();
    protected final GameAssetManager assetManager = GameAssetManager.getInstance();

    //window background (clear) color
    protected Color bgColor = Color.BLACK;

    /**
     * default constructor
     *
     * @param game instance of game
     */
    public GameGUI (Game game) {
        this.game = game;
        this.screenManager = new DefaultScreenManager(this.game);
    }

    @Override
    public void create() {
        //add screens
        this.screenManager.addScreen(Screens.MAIN_MENU, new MainMenuScreen());

        //activate screen
        this.screenManager.leaveAllAndEnter(Screens.MAIN_MENU);
    }

    @Override
    public void resize(int width, int height) {
        LocalLogger.print("resize window.");

        this.screenManager.resize(width, height);
    }

    @Override
    public void render() {
        //first, update game time
        this.time.setTime(System.currentTimeMillis());
        this.time.setDelta(Gdx.graphics.getDeltaTime());

        //update FPS
        fps.setFPS(Gdx.graphics.getFramesPerSecond());

        //show FPS warning, if neccessary
        fps.showWarningIfNeccessary();

        //execute tasks, which should be executed in OpenGL context thread
        Platform.executeQueue();

        //load assets
        assetManager.update();

        try {
            //process input
            this.screenManager.processInput();

            //update screens
            this.screenManager.update();

            //clear all color buffer bits and clear screen
            Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //draw screens
            this.screenManager.draw();
        } catch (Exception e) {
            LocalLogger.printStacktrace(e);
            Gdx.app.error("BaseGame", "exception thrown while render game: " + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        this.screenManager.dispose();
    }

}
