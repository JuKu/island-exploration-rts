package com.jukusoft.rts.gui.screens.impl;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.utils.Platform;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.screens.Screens;
import com.teamunify.i18n.I;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoadGameScreen extends GUIScreen {

    protected AtomicBoolean gameLoaded = new AtomicBoolean(false);

    @Override
    protected void start(Game game, ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    protected void stop(Game game) {
        //
    }

    @Override
    protected void resume(Game game) {
        GameScreen gameScreen = this.screenManager.getScreenByName(Screens.PLAY_GAME, GameScreen.class);

        //start another thread to load game
        new Thread(() -> {
            //load maps, resouces and so on asynchronous in extra thread
            gameScreen.loadAsync();

            //map loading finished, so set flag
            this.gameLoaded.set(true);
        }).start();
    }

    @Override
    protected void pause(Game game) {
        //
    }

    @Override
    protected void resize(int width, int height) {
        //
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {
        if (gameLoaded.get()) {
            LocalLogger.print("map loaded successfully.");

            //TODO: init island renderer

            LocalLogger.print(I.tr("game loaded successfully."));

            //enter game screen
            Platform.runOnUIThread(() -> this.screenManager.leaveAllAndEnter(Screens.PLAY_GAME));
        }
    }

}
