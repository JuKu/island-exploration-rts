package com.jukusoft.rts.gui.screens;

import com.jukusoft.rts.game.Game;

public class OtherDummyScreen implements IScreen {

    protected String someVar = "";

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void onStop(Game game) {

    }

    @Override
    public void onResume(Game game) {

    }

    @Override
    public void onPause(Game game) {

    }

    @Override
    public void onResize(int width, int height) {

    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return false;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    public void draw(Game game) {

    }
}
