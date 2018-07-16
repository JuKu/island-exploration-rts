package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.input.InputManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;

public class HUDScreen implements IScreen {

    //paths
    protected static final String LOGO_PATH = "./data/misc/logo/logo.png";

    //stage which contains widgets
    protected Stage stage = null;

    //global asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //flag, if HUD should be drawn
    protected boolean show = true;

    //widgets
    protected Image logo = null;

    public HUDScreen () {
        //
    }

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        //create new stage
        this.stage = new Stage();

        //load assets
        this.assetManager.load(LOGO_PATH, Texture.class);
    }

    @Override
    public void onStop(Game game) {
        this.assetManager.unload(LOGO_PATH);
    }

    @Override
    public void onResume(Game game) {
        //add input processor
        InputManager.getInstance().addFirst(this.stage);

        assetManager.finishLoading(LOGO_PATH);
        Texture logoTexture = assetManager.get(LOGO_PATH, Texture.class);
        this.logo = new Image(logoTexture);
        stage.addActor(this.logo);
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

        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        logo.setY(stage.getHeight() - logo.getHeight());
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
