package com.jukusoft.rts.gui.screens.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jukusoft.rts.core.Game;
import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.version.Version;
import com.jukusoft.rts.gui.assetmanager.GameAssetManager;
import com.jukusoft.rts.gui.screens.IScreen;
import com.jukusoft.rts.gui.screens.ScreenManager;
import com.jukusoft.rts.gui.utils.SkinFactory;

public abstract class GUIScreen implements IScreen {

    protected Stage stage = null;
    protected GameAssetManager assetManager = GameAssetManager.getInstance();
    protected Skin skin = null;
    protected Skin skin2 = null;
    protected ScreenManager<IScreen> screenManager = null;
    protected Pixmap labelColor = null;

    //images
    protected Image screenBG = null;

    //label
    protected Label versionLabel = null;

    protected static final String BG_PATH = "data/misc/wallpaper/ocean/Ocean_large.png";

    @Override
    public final void onStart(Game game, ScreenManager<IScreen> screenManager) {
        this.screenManager = screenManager;

        //create skin
        String atlasFile = "data/misc/skins/default/uiskin.atlas";
        String jsonFile = "data/misc/skins/default/uiskin.json";
        LocalLogger.print("create skin, atlas file: " + atlasFile + ", json file: " + jsonFile);
        this.skin = SkinFactory.createSkin(jsonFile);

        this.skin2 = SkinFactory.createSkin("./data/misc/skins/libgdx/uiskin.json");

        //create UI stage
        this.stage = new Stage();

        this.start(game, screenManager);
    }

    protected abstract void start (Game game, ScreenManager<IScreen> screenManager);

    @Override
    public final void onStop(Game game) {
        this.skin.dispose();
        this.skin = null;

        this.skin2.dispose();
        this.skin2 = null;

        this.stop(game);
    }

    protected abstract void stop (Game game);

    @Override
    public final void onResume(Game game) {
        //load texures
        assetManager.load(BG_PATH, Texture.class);

        assetManager.finishLoading();

        Texture bgTexture = assetManager.get(BG_PATH, Texture.class);
        this.screenBG = new Image(bgTexture);

        //add widgets to stage
        stage.addActor(screenBG);

        //get client version
        Version version = Version.getInstance();

        this.versionLabel = new Label("Version: " + version.getFullVersion(), this.skin2);

        //set label background color
        labelColor = new Pixmap((int) this.versionLabel.getWidth(), (int) this.versionLabel.getHeight(), Pixmap.Format.RGBA8888);
        labelColor.setColor(Color.valueOf("#001f3f"));//#36581a, #0074D9, #001f3f
        labelColor.fill();
        this.versionLabel.getStyle().background = new Image(new Texture(labelColor)).getDrawable();
        stage.addActor(versionLabel);

        //set input processor
        Gdx.input.setInputProcessor(stage);

        this.resume(game);
    }

    protected abstract void resume (Game game);

    @Override
    public final void onPause(Game game) {
        assetManager.unload(BG_PATH);

        labelColor.dispose();

        this.pause(game);
    }

    protected abstract void pause (Game game);

    @Override
    public final void onResize(int width, int height) {
        stage.getViewport().setScreenWidth(width);
        stage.getViewport().setScreenHeight(height);
        stage.getViewport().update(width, height, true);
        stage.getViewport().setScreenPosition(0, 0);
        stage.getViewport().setScreenSize(width, height);

        //make the background fill the screen
        screenBG.invalidate();

        versionLabel.setX(20);
        versionLabel.setY(20);

        this.resize(width, height);
    }

    protected abstract void resize (int width, int height);

    @Override
    public boolean processInput(Game game, ScreenManager<IScreen> screenManager) {
        return true;
    }

    @Override
    public void update(Game game, ScreenManager<IScreen> screenManager) {

    }

    @Override
    public final void draw(Game game) {
        //show the main menu screen
        stage.act();
        stage.draw();
    }

}
