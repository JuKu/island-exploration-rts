package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.fps.FPSManager;
import com.jukusoft.rts.gui.input.InputManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.utils.SkinFactory;

public class HUDScreen implements IScreen {

    //paths
    protected static final String LOGO_PATH = "./data/misc/logo/logo.png";

    //stage which contains widgets
    protected Stage stage = null;

    protected Pixmap labelColor = null;

    //global asset manager
    protected GameAssetManager assetManager = GameAssetManager.getInstance();

    //flag, if HUD should be drawn
    protected boolean show = true;

    //skins
    protected Skin skin2 = null;

    //widgets
    protected Image logo = null;
    protected Label fpsLabel = null;

    public HUDScreen () {
        //
    }

    @Override
    public void onStart(Game game, ScreenManager<IScreen> screenManager) {
        //create new stage
        this.stage = new Stage();

        //load assets
        this.assetManager.load(LOGO_PATH, Texture.class);

        //load skins
        this.skin2 = SkinFactory.createSkin("./data/misc/skins/libgdx/uiskin.json");
    }

    @Override
    public void onStop(Game game) {
        this.assetManager.unload(LOGO_PATH);
    }

    @Override
    public void onResume(Game game) {
        //add input processor
        InputManager.getInstance().addFirst(this.stage);

        //add logo
        assetManager.finishLoading(LOGO_PATH);
        Texture logoTexture = assetManager.get(LOGO_PATH, Texture.class);
        this.logo = new Image(logoTexture);
        stage.addActor(this.logo);

        this.fpsLabel = new Label("FPS: ????", this.skin2);

        //set label background color
        labelColor = new Pixmap((int) this.fpsLabel.getWidth(), (int) this.fpsLabel.getHeight(), Pixmap.Format.RGBA8888);
        labelColor.setColor(Color.valueOf("#001f3f"));//#36581a, #0074D9, #001f3f
        labelColor.fill();
        this.fpsLabel.getStyle().background = new Image(new Texture(labelColor)).getDrawable();
        stage.addActor(fpsLabel);
    }

    @Override
    public void onPause(Game game) {
        //remove input processor
        InputManager.getInstance().remove(this.stage);
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().setScreenPosition(0, 0);
        stage.getViewport().setScreenSize(width, height);

        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        logo.setY(stage.getHeight() - logo.getHeight());

        fpsLabel.setX(20);
        fpsLabel.setY(20);
    }

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return false;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {
        //update FPS
        fpsLabel.setText("FPS: " + FPSManager.getInstance().getFPS());
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
