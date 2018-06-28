package com.jukusoft.rts.gui.screens.impl;

import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;

public class GameScreen implements IScreen {

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
        //
    }

    @Override
    public void onPause(Game game) {
        //
    }

    @Override
    public void onResize(int width, int height) {
        //
    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return false;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {
        //
    }

    @Override
    public void draw(Game game) {
        //
    }

    /**
    * will be executed from LoadGameScreen in another thread
    */
    public void loadAsync () {
        //load resources asynchronous
    }

}
