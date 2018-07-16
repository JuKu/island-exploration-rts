package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.input.InputManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;

public class HUDScreen implements IScreen {

    //stage which contains widgets
    protected Stage stage = null;

    //global asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //flag, if HUD should be drawn
    protected boolean show = true;

    public HUDScreen () {
        //
    }

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        //create new stage
        this.stage = new Stage();
    }

    @Override
    public void onStop(Game game) {

    }

    @Override
    public void onResume(Game game) {
        //add input processor
        InputManager.getInstance().addFirst(this.stage);
    }

    @Override
    public void onPause(Game game) {
        //remove input processor
        InputManager.getInstance().remove(this.stage);
    }

    @Override
    public void onResize(int width, int height) {
        /*stage.getViewport().setScreenWidth(width);
        stage.getViewport().setScreenHeight(height);
        stage.getViewport().update(width, height, true);*/
        stage.getViewport().setScreenPosition(0, 0);
        stage.getViewport().setScreenSize(width, height);
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
        if (!this.show) {
            return;
        }

        //draw widgets
        stage.act();
        stage.draw();
    }

}
